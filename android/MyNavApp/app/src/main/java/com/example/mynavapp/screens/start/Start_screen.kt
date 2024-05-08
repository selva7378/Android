package com.example.mynavapp.screens.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.mynavapp.R
import com.example.mynavapp.databinding.FragmentStartScreenBinding


class start_screen : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
         val binding: FragmentStartScreenBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_start_screen, container, false)

        binding.buttonPlayFlames.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_start_screen_to_game_screen)
        )
        return binding.root
    }

}