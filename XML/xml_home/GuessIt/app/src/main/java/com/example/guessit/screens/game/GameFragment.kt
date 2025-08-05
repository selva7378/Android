package com.example.android.guesstheword.screens.game

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.guessit.R
import com.example.guessit.databinding.GameFragmentBinding
import com.example.guessit.screens.game.BuzzType
import com.example.guessit.screens.game.GameViewModel

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {



    private val gameViewModel: GameViewModel by viewModels()

    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )

        binding.gameViewModel = gameViewModel

        // the below powerfull piece of code allows us to use live data to automaticaly update the my data binding layouts.
        binding.lifecycleOwner = viewLifecycleOwner


        gameViewModel.eventGameFinish.observe(viewLifecycleOwner, { hasGameFinished ->
            if(hasGameFinished){
                gameViewModel.onGameFinishComplete()
                gameFinished()
            }
        })

        gameViewModel.startBuzzing.observe(viewLifecycleOwner, { startBuzzer ->
            if(startBuzzer) {
                buzz(BuzzType.GAME_OVER.pattern)
            }
        })
        return binding.root

    }



    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
        val action = GameFragmentDirections.actionGameToScore(gameViewModel.score.value)
        findNavController().navigate(action)
//        Toast.makeText(this.activity, "selvaa", Toast.LENGTH_SHORT).show()
    }

    private fun buzz(pattern: LongArray) {
        val buzzer = activity?.getSystemService<Vibrator>()

        buzzer?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                //deprecated in API 26
                buzzer.vibrate(pattern, -1)
            }
        }
    }

}
