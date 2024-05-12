package com.example.newsweather.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsweather.R
import com.example.newsweather.database.roomdb.NewsDatabase
import com.example.newsweather.databinding.FragmentListingscreenBinding
import com.example.newsweather.recycler.AdapterClass
import com.example.newsweather.recycler.NewsListingAdapterClass
import com.example.newsweather.viewmodels.NewsViewModel
import com.example.newsweather.viewmodels.NewsViewModelFactory

class Listingscreen : Fragment() {
    private lateinit var listingRecyclerView: RecyclerView
    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var newsListingAdapter: NewsListingAdapterClass

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
        val binding: FragmentListingscreenBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_listingscreen, container, false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = NewsDatabase.getInstance(application).newsDatabaseDao

        val newsViewModelFactory = NewsViewModelFactory(dataSource, application)

        val newsViewModel = ViewModelProvider(this, newsViewModelFactory)
            .get(NewsViewModel::class.java)

        newsViewModel.getMyData()
        newsViewModel.dbRetrieve()






        listingRecyclerView = binding.recyclerView
        listingRecyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        listingRecyclerView.setHasFixedSize(true)

        categoryRecyclerView = binding.recyclerViewCategory
        categoryRecyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        categoryRecyclerView.setHasFixedSize(true)

        val immutableList = listOf("national", "business",
            "sports", "world",
            "politics", "technology", "startup", "entertainment",
            "miscellaneous", "hatke", "science", "automobile")
        categoryRecyclerView.adapter = AdapterClass(immutableList,  newsViewModel)
        val navController = findNavController()
        newsListingAdapter = NewsListingAdapterClass(navController)
        listingRecyclerView.adapter = newsListingAdapter



        Log.e("listingscreen", newsViewModel.allDataListFromRoom.toString())
        newsViewModel.allDataListFromRoom.observe(viewLifecycleOwner) { dataList ->
            // Update the RecyclerView adapter with the new data list
            newsListingAdapter.addData(dataList)
            Log.i("listing screen live data size", newsViewModel.allDataListFromRoom.value?.size.toString())
        }
        newsViewModel._categoryDataListFromRoom.observe(viewLifecycleOwner){dataList ->
            newsListingAdapter.addData(dataList)

        }

        listingRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    newsViewModel.dbRetrieve()
                }
            }
        })
//        recyclerView.adapter = AdapterClass(newsViewModel.dataListFromRoom)


        return binding.root
    }

}