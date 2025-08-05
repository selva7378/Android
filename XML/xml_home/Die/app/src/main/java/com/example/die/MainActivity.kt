package com.example.die

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * This activity allows the user to roll a dice and view the result
 * on the screen.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val button: Button = findViewById(R.id.button)

        button.setOnClickListener {
            rollDice(6)
            Toast.makeText(this, "Dice Rolled!", Toast.LENGTH_SHORT).show()
        }

        // Do a dice roll when the app starts
        rollDice(6)
    }
}

/**
 * Roll the dice and update the screen with the result.
 */
private fun MainActivity.rollDice(i: Int) {
    // Create new Dice object with 6 sides and roll it
    val dice = Dice(i)
    val diceRoll = dice.roll()

    // Update the screen with the dice roll
    val resultView = findViewById<ImageView>(R.id.die1_imageView)
    var drawableResource = when(diceRoll){
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }

    // Update the ImageView with the correct drawable resource ID
    resultView.setImageResource(drawableResource)

    // Update the content description
    resultView.contentDescription = diceRoll.toString()
}

private class Dice(private val numSides: Int) {
    fun roll(): Int {
        return (1..numSides).random()

    }
}