package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tipcalculator.databinding.ActivityMainBinding
import com.google.android.material.color.DynamicColors
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val initialPadding = with(binding.root){
            mapOf(
                "left" to paddingLeft,
                "top" to paddingTop,
                "right" to paddingRight,
                "bottom" to paddingBottom
            )
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Preserve original padding from XML (like android:padding="24dp")
            v.setPadding(
                systemBars.left + initialPadding["left"]!!,
                systemBars.top + initialPadding["top"]!!,
                systemBars.right + initialPadding["right"]!!,
                systemBars.bottom + initialPadding["bottom"]!!
            )
            insets
        }

        binding.btnCalculate.setOnClickListener { calculateTip() }

    }


}

private fun MainActivity.calculateTip() {
    val stringInTextField = binding.etCostOfService.editText?.text.toString()
    val cost = stringInTextField.toDoubleOrNull()
    if (cost == null) {
        displayTip(0.0)
        return
    }
    val percentage: Double = when (binding.rgTipOptions.checkedRadioButtonId) {
        R.id.rb_amazing -> 0.20
        R.id.rb_good -> 0.18
        else -> 0.15
    }
    var tip = cost * percentage
    val roundUp = binding.swRoundUp.isChecked
    if (roundUp) {
        tip = kotlin.math.ceil(cost * percentage)
    }
    displayTip(tip)
}

private fun MainActivity.displayTip(tip : Double) {
    val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
    binding.tvTipResult.text = getString(R.string.tip_amount, formattedTip)
}