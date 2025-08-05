package com.example.bmi.screens.home

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.bmi.R
import com.example.bmi.databinding.FragmentCheckYourHealthBinding

class fragmentCheckYourHealth : Fragment() {


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
        val binding: FragmentCheckYourHealthBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_check_your_health, container, false
        )
        binding.animationView.playAnimation()

        //TEXT GRADIENT CODE BELOW
        val txtCheck = binding.textView
        val paint = txtCheck.paint
        val width = paint.measureText(txtCheck.text.toString())
        txtCheck.paint.shader = LinearGradient(
            0f, 0f, width, txtCheck.textSize, intArrayOf(
                Color.parseColor("#FF0000"),
                Color.parseColor("#8338ec"),
                Color.parseColor("#c8b6ff"),
            ), null, Shader.TileMode.REPEAT
        )

        binding.buttonCheck.setOnClickListener{
            findNavController().navigate(fragmentCheckYourHealthDirections.actionFragmentCheckYourHealthToMain2())
        }
        return binding.root
    }
}