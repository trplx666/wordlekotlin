package com.example.wordle

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val listWords = listOf(
        "apple", "house", "table", "chair", "clock", "phone", "plant", "dress", "spoon",
        "cloud", "grass", "bread", "snake", "music", "pizza", "beach", "river", "horse",
        "grape", "piano", "shoes", "light", "lemon", "brush", "boots", "movie", "radio",
        "glass", "candy", "flute"
    )
    private val wordleWord = listWords.random().toUpperCase()
    private val targetWord = wordleWord
    private var currentAttempt = 0
    private var inputText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editText = findViewById<EditText>(R.id.editText)
        val button = findViewById<Button>(R.id.button)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)

        val squares = Array(5) { i ->
            Array(5) { j ->
                findViewById<TextView>(resources.getIdentifier("square${i + 1}_${j + 1}", "id", packageName))
            }
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                inputText = s?.toString() ?: ""
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        button.setOnClickListener {
            val userInput = inputText.toUpperCase()
            if (userInput.length != 5) {
                resultTextView.text = "Введите слово из 5 букв"
                return@setOnClickListener
            }

            for (i in userInput.indices) {
                val letter = userInput[i]
                val targetLetter = targetWord[i]
                val square = squares[currentAttempt][i]

                square.text = letter.toString()

                if (letter == targetLetter) {
                    square.setBackgroundColor(Color.GREEN)
                } else if (targetWord.contains(letter, ignoreCase = true)) {
                    square.setBackgroundColor(Color.MAGENTA)
                } else {
                }
            }

            if (userInput == targetWord) {
                resultTextView.text = "Вы выиграли!"
            } else {
                if (currentAttempt < squares.size - 1) {
                    currentAttempt++
                } else {
                    resultTextView.text = "Попробуйте снова!"
                }
            }
        }
    }
}
