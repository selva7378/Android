package com.example.bmicalculator

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
//import androidx.databinding.DataBindingUtil
import com.example.bmicalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.buttonCalculate.setOnClickListener {
        calculateBMI()
        }





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun calculateBMI() {
        val weightStr = binding.editTextWeight.text.toString()
        val heightStr = binding.editTextHeight.text.toString()

        if (weightStr.isNotEmpty() && heightStr.isNotEmpty()) {
            val weight = weightStr.toDouble()
            val height = heightStr.toDouble() / 100;

            val bmi = weight / (height * height)

            displayBMI(bmi)
        } else {
            binding.resultTextView.text = "Please enter your weight and height."
            binding.resultTextView.visibility = View.VISIBLE
        }

    }

    private fun displayBMI(bmi: Double) {
        val bmiText = "Your BMI is %.2f".format(bmi)
        val bmiResult = when {
            bmi < 18.5 -> "$bmiText\nUnderweight"
            bmi >= 18.5 && bmi < 25 -> "$bmiText\nNormal weight"
            bmi >= 25 && bmi < 30 -> "$bmiText\nOverweight"
            else -> "$bmiText\nObesity"
        }
        binding.resultTextView.text = bmiResult
        binding.resultTextView.visibility = View.VISIBLE
    }
}