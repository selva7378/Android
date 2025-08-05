package com.example.bmi.screens.result

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.bmi.R
import com.example.bmi.databinding.FragmentCheckYourHealthBinding
import com.example.bmi.databinding.FragmentMainBinding
import com.example.bmi.databinding.FragmentResultBinding


class result : Fragment() {
    private lateinit var binding: FragmentResultBinding

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
            inflater, R.layout.fragment_result, container, false
        )

        //TEXT GRADIENT CODE BELOW
        val txtCheck = binding.textViewResult
        val paint = txtCheck.paint
        val width = paint.measureText(txtCheck.text.toString())
        txtCheck.paint.shader = LinearGradient(
            0f, 0f, width, txtCheck.textSize, intArrayOf(
                Color.parseColor("#FF0000"),
                Color.parseColor("#8338ec"),
                Color.parseColor("#c8b6ff"),
            ), null, Shader.TileMode.REPEAT
        )

//        binding.animationView.playAnimation()
        val mainFragmentArgs by navArgs<resultArgs>()
        displayBMI(mainFragmentArgs.bmiResult)
        return binding.root
    }

    private fun displayBMI(bmi: Float) {
        val bmiText = "Your BMI is %.2f".format(bmi)
        val bmiResult: String
        val animationResId: Int
        if(bmi < 18.5){
            bmiResult = "$bmiText\nUnderweight eat well and do workout"
            animationResId = R.raw.man_lifting_barbell
        }else if (bmi >= 18.5 && bmi < 25){
            bmiResult = "$bmiText\nNormal weight"
            animationResId = R.raw.bouncing_heart
        }else if(bmi >= 25 && bmi < 30){
            bmiResult = "$bmiText\nOverweight follow proper diet and do cardio"
            animationResId = R.raw.girl_running_on_treadmill
        }else{
            bmiResult = "$bmiText\nObesity change your life style else it's game over"
            animationResId = R.raw.gameover
        }

        // Set the animation resource to the LottieAnimationView
        binding.animationViewResult.setAnimation(animationResId)
        binding.animationViewResult.playAnimation()
        binding.textViewResult.text = bmiResult
//        binding.resultTextView.visibility = View.VISIBLE
    }
}