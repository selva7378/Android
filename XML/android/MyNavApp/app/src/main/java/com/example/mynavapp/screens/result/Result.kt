package com.example.mynavapp.screens.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mynavapp.R
import com.example.mynavapp.databinding.FragmentResultBinding

//, TextToSpeech.OnInitListener
class result : Fragment() {
//    private lateinit var textToSpeech: TextToSpeech
    private lateinit var viewModel: ResultViewModel
    private lateinit var viewModelFactory: ResultViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentResultBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_result, container, false)
        val gameFragmentArgs by navArgs<resultArgs>()

        viewModelFactory = ResultViewModelFactory(gameFragmentArgs.flamesResult)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ResultViewModel::class.java)

        binding.resultViewModel = viewModel
        binding.textView2.text = gameFragmentArgs.flamesResult



        viewModel.eventPlayAgain.observe(viewLifecycleOwner, Observer { playAgain ->
            if (playAgain) {
                viewModel.onPlayAgainComplete()
                findNavController().navigate(resultDirections.actionResultToStartScreen())
            }
        })

        return binding.root


        //        textToSpeech = TextToSpeech(requireContext(), this)
//        {status ->
//            if(status == TextToSpeech.SUCCESS){
//                val result = textToSpeech.setLanguage(Locale.ENGLISH)
//                if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
//                    Log.i("result", "language is not supported")
//                }
//            }
//        }
//        if(flamesResult.isNotEmpty()){
//            textToSpeech.speak(flamesResult, TextToSpeech.QUEUE_FLUSH, null, null)
//        }else{
//            Log.i("result fragment", "flames result is emtpy.")
//        }
    }

//    override fun onInit(status: Int) {
//        if(status == TextToSpeech.SUCCESS){
//            val  result = textToSpeech.setLanguage(Locale.CHINESE)
//            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
//                Log.e("result fragment", "langauge not supported")
//            }
//            textToSpeech.speak(flamesResult, TextToSpeech.QUEUE_FLUSH, null, null)
//        }else{
//            Log.e("result fragment", "initialization failed")
//        }
//    }
//
//    fun releaseTextToSpeech(){
//        textToSpeech.stop();
//        textToSpeech.shutdown()
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        releaseTextToSpeech()
//    }
}