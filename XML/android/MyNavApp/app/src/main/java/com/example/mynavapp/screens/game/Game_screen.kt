package com.example.mynavapp.screens.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.mynavapp.R
import com.example.mynavapp.databinding.FragmentGameScreenBinding

class game_screen : Fragment() {
    private val viewModel: GameViewModel by viewModels()

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
        val binding: FragmentGameScreenBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game_screen, container, false
        )

        binding.gameViewModel = viewModel
        binding.lifecycleOwner = this

        binding.buttonFlames.setOnClickListener {view: View ->
            val name: String = binding.editTextText.text.toString()
            val crushName: String = binding.editTextText2.text.toString()
            viewModel.calculateFlames(name, crushName)
            val flamesResult: String = viewModel.flamesResult.value ?: ""
            view.findNavController().navigate(game_screenDirections.actionGameScreenToResult(flamesResult))
        }
        return binding.root
    }


}