package com.example.marsrealestate.overview


import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.marsrealestate.R
import com.example.marsrealestate.databinding.FragmentOverviewBinding
import com.example.marsrealestate.network.MarsApiFilter


/**
 * This fragment shows the the status of the Mars real-estate web services transaction.
 */
class OverviewFragment : Fragment() {

    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    // Use a nullable backing property for the binding to handle view lifecycle
    private var _binding: FragmentOverviewBinding? = null
    // This property will only be valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    // Declare the adapter as a member variable, initialized once
    private lateinit var photoGridAdapter: PhotoGridAdapter

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout and assign it to the backing property
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        val view = binding.root

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        setHasOptionsMenu(true)
        return view
    }

    /**
     * Sets up the RecyclerView with a GridLayoutManager and the PhotoGridAdapter.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the adapter here, once per fragment instance
        photoGridAdapter = PhotoGridAdapter({
            // Handle click events for each photo item
            val action = OverviewFragmentDirections.actionShowDetail(it)
            findNavController().navigate(action)
        })

        binding.photosGrid.apply {
            // Use a GridLayoutManager with 2 columns
            layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 2)
            // Set the adapter to the PhotoGridAdapter (the single instance)
            adapter = photoGridAdapter
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
            when (item.itemId) {
                R.id.show_rent_menu -> MarsApiFilter.SHOW_RENT
                R.id.show_buy_menu -> MarsApiFilter.SHOW_BUY
                else -> MarsApiFilter.SHOW_ALL
            }
        )
        return true
    }

    /**
     * Clear the binding reference when the view is destroyed to prevent memory leaks.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Inflates the overflow menu that contains filtering options.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
