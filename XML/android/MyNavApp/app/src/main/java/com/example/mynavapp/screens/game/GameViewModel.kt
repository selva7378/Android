package com.example.mynavapp.screens.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController

class GameViewModel: ViewModel(){

    private val _flamesResult = MutableLiveData<String>()
    val flamesResult: LiveData<String>
        get() = _flamesResult

//    val name: String =  binding.editTextText.text.toString()
//    val crushName: String = binding.editTextText2.text.toString()

    init {
        Log.i("GameViewModel", "GameViewModel created!")
    }

    fun calculateFlames(name1: String, name2: String) {
        val name1Chars = name1.lowercase().toCharArray().filter { it != ' ' }
        val name2Chars = name2.lowercase().toCharArray().filter { it != ' ' }
        println("after $name1Chars")
        println("after $name2Chars")


        // Remove common characters
        val commonChars = mutableListOf<Char>()
        for (char in name1Chars) {
            if (char in name2Chars && char !in commonChars) {
                commonChars.add(char)
            }
        }

        // Calculate remaining characters
        val remainingChars = (name1Chars + name2Chars).filter { it !in commonChars }
        println(remainingChars)

        // Calculate FLAMES result
        val flamesCount = remainingChars.size % 6
        println(flamesCount)
        val result = when (flamesCount) {
            1 -> "Friendship"
            2 -> "Love"
            3 -> "Affection"
            4 -> "Marriage"
            5 -> "Enemy"
            else -> "Sibling"
        }
        _flamesResult.value = result;

    }

//    fun onSkip() {
//        _score.value = (score.value)?.minus(1)
//        nextWord()
//    }
//    binding.buttonFlames.setOnClickListener {view: View ->
//
//        val flamesResult: String = binding.gameViewModel.calculateFlames(name, crushName)
//        view.findNavController().navigate(game_screenDirections.actionGameScreenToResult(flamesResult))
//    }

}