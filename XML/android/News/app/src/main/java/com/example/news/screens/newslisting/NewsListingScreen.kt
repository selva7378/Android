package com.example.news.screens.newslisting

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.databinding.FragmentNewsListingScreenBinding
import com.example.news.recycler.AdapterClass
import com.example.news.recycler.NewsListingAdapterClass
import com.example.news.roomdb.news.NewsDatabase
import com.example.news.viewmodels.newsviewmodel.NewsViewModel
import com.example.news.viewmodels.newsviewmodel.NewsViewModelFactory
import com.example.news.viewmodels.weatherviewmodel.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale

class newsListingScreen : Fragment() {


    private lateinit var listingRecyclerView: RecyclerView
    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var newsListingAdapter: NewsListingAdapterClass
    private lateinit var binding: FragmentNewsListingScreenBinding
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var weatherViewModel: WeatherViewModel


    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    // Handler for pagination delay
    private val handler = Handler(Looper.getMainLooper())
    init {

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_news_listing_screen, container, false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = NewsDatabase.getInstance(application).newsDatabaseDao

        val newsViewModelFactory = NewsViewModelFactory(dataSource, application)

        newsViewModel = ViewModelProvider(this, newsViewModelFactory)
            .get(NewsViewModel::class.java)
        weatherViewModel = WeatherViewModel()

//        Log.i("newslistingscreen", ;a)


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        checkLocationPermission()




//      network availablity checking
        if(newsViewModel.isNetworkAvailable()){
            newsViewModel.dbClear()
        }else{
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.network)
            binding.extendedFab.icon = drawable
        }
        newsViewModel.getMyData()






        listingRecyclerView = binding.recyclerView
        listingRecyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        listingRecyclerView.setHasFixedSize(true)

        categoryRecyclerView = binding.recyclerViewCategory
        categoryRecyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        categoryRecyclerView.setHasFixedSize(true)

        val immutableList = listOf("all", "national", "business",
            "sports", "world",
            "politics", "technology", "startup", "entertainment",
            "miscellaneous", "hatke", "science", "automobile")
        categoryRecyclerView.adapter = AdapterClass(immutableList,  newsViewModel)
        val navController = findNavController()
        newsListingAdapter = NewsListingAdapterClass(navController)
        listingRecyclerView.adapter = newsListingAdapter



        //observers
        Log.e("listingscreen", newsViewModel.allDataListFromRoom.toString())
        newsViewModel.allDataListFromRoom.observe(viewLifecycleOwner) { dataList ->
            // Update the RecyclerView adapter with the new data list
            newsListingAdapter.addData(dataList)
            Log.i("listing screen live data size", newsViewModel.allDataListFromRoom.value?.size.toString())
        }
        newsViewModel._categoryDataListFromRoom.observe(viewLifecycleOwner){dataList ->
            if(dataList != null){
                newsListingAdapter.clearData()
                newsListingAdapter.addData(dataList)
            }
        }
        weatherViewModel.temperature.observe(viewLifecycleOwner){
            binding.extendedFab.text = it
//            val temperature = it?.toDoubleOrNull()
            // Extract the numeric part from the temperature string
            val temperature = it?.split(" ")?.firstOrNull()?.toDoubleOrNull()
            Log.i("listing screen selva", temperature.toString() + "selva")
            if (temperature != null) {
                val drawableId = when {
                    temperature <= 20.0 -> R.drawable.freezing
                    else -> R.drawable.hot
                }
                val drawable = ContextCompat.getDrawable(requireContext(), drawableId )
                binding.extendedFab.icon = drawable
            }else{
                val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.network)
                binding.extendedFab.icon = drawable
            }
        }

        newsViewModel.dataLoading.observe(viewLifecycleOwner) { isLoading ->
            if (!isLoading) {
                newsViewModel.dbRetrieve()
            }
        }


        //adapter pagination scroller code
        listingRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    binding.progressBar.visibility = View.VISIBLE
                    handler.postDelayed({
                        if(newsViewModel.category == "all"){
                        Log.i("Listingscreen", "all scrolling")
                        newsViewModel.dbRetrieve()
                    }else if(newsViewModel.searchFlag == true){
                        Log.i("Listingscreen", "search scrolling")
                        newsViewModel.dbRetrieveBySearch(newsViewModel.category, "scrollview")
                    }else{
                        Log.i("Listingscreen", "category scrolling")
                        newsViewModel.dbRetrieveCategory(newsViewModel.category, "allData")
                    }
                        binding.progressBar.visibility = View.INVISIBLE
                    }, 1000)

                }
            }
        })

        //click listener
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Perform search when user submits query (e.g., press search button)
                newsViewModel.dbRetrieveBySearch(query, "searchview")
                newsViewModel.currentPage = 0
                newsViewModel.category = query
                newsViewModel.searchFlag = true
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Perform search as user types (optional)
//                performSearch(newText)
                return true
            }
        })

        binding.extendedFab.setOnClickListener{
            weatherViewModel.getMyWeatherData(weatherViewModel.location)
        }

        return binding.root
    }


    // location code
    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            getLastKnownLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    // Use the location object
                    val latitude = it.latitude
                    val longitude = it.longitude
                    weatherViewModel.location = getAddressFromLocation(latitude, longitude).split(",")[2]
                    weatherViewModel.getMyWeatherData(weatherViewModel.location)
                    // Update UI or handle the location object
                } ?: run {
                    // Location is null, handle the case where location isn't available
                }
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastKnownLocation()
            } else {
                // Permission denied, handle it appropriately
            }
        }
    }

    private fun getAddressFromLocation(latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        var locationName: String = ""
        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses?.isNotEmpty()!!) {
                val address = addresses[0]
                val addressLine = address.getAddressLine(0)
                val city = address.locality
                val state = address.adminArea
                val country = address.countryName
                val postalCode = address.postalCode
                locationName = addresses?.get(0)?.getAddressLine(0)!!

                // Handle or display the address details
                Log.d("LocationFragment", "Address: $addressLine, City: $city, State: $state, Country: $country, PostalCode: $postalCode")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("LocationFragment", "Geocoder failed: ${e.message}")
        }
        return locationName
    }
}