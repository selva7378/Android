package com.example.bmi.screens.main

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.bmi.R
import com.example.bmi.databinding.FragmentCheckYourHealthBinding
import com.example.bmi.databinding.FragmentMainBinding
import com.example.bmi.screens.home.fragmentCheckYourHealthDirections

class main : Fragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false
        )

        //TEXT GRADIENT CODE BELOW
        val txtCheck = binding.textView2
        val paint = txtCheck.paint
        val width = paint.measureText(txtCheck.text.toString())
        txtCheck.paint.shader = LinearGradient(
            0f, 0f, width, txtCheck.textSize, intArrayOf(
                Color.parseColor("#FF0000"),
                Color.parseColor("#8338ec"),
                Color.parseColor("#c8b6ff"),
            ), null, Shader.TileMode.REPEAT
        )

        // binding.animationView.playAnimation()
        binding.buttonCalculate.setOnClickListener{
            calculateBMI()

        }
        return binding.root
    }

    private fun calculateBMI() {
        val weightStr = binding.editTextWeight.text.toString()
        val heightStr = binding.editTextHeight.text.toString()
        binding.editTextWeight.text?.clear()
        binding.editTextHeight.text?.clear()

        val weight = weightStr.toFloatOrNull()
        val height = heightStr.toFloatOrNull()

        if (weight != null && height != null && weight > 0 && height > 0) {
            val heightInMeters = height / 100
            val bmi = weight / (heightInMeters * heightInMeters)
            findNavController().navigate(mainDirections.actionMain2ToResult(bmi))
        } else {
            Toast.makeText(requireContext(), "Please enter valid weight and height", Toast.LENGTH_SHORT).show()
        }
    }
}