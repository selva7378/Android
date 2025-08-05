package com.example.androidtrivia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.example.androidtrivia.databinding.FragmentTitleBinding


class TitleFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var binding: FragmentTitleBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_title, container, false)
        binding.playButton.setOnClickListener {
            findNavController().navigate(R.id.action_titleFragment2_to_gameFragment)
        }

        return binding.root

    }

    // Inside your Fragment class

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the MenuHost
        val menuHost: MenuHost = requireActivity()

        // Add a menu provider to the MenuHost
        menuHost.addMenuProvider(object : MenuProvider {
            // This is where you inflate the menu
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.overflow_menu, menu)
            }

            // This is where you handle menu item clicks
            // Let NavigationUI handle the mapping automatically
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                val navController = findNavController()
                return menuItem.onNavDestinationSelected(navController) || false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}