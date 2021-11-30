package ciaran.malone.ca2mobileapp.activities

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ciaran.malone.ca2mobileapp.R
import ciaran.malone.ca2mobileapp.databinding.ActivityGameBinding
import ciaran.malone.ca2mobileapp.models.ScoreModel
import com.google.android.material.snackbar.Snackbar
import java.util.*
import timber.log.Timber
import timber.log.Timber.i


class Game : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding

    var scoreBoard = ScoreModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())
        i("started")

        binding.submitScoreButton.setOnClickListener {
            i("BUTTON PRESSED")

            val scoreText = binding.scoreInputField.text.toString()
            val nameText = binding.nameInputField.text.toString()
            if (scoreText.isNotEmpty() and nameText.isNotEmpty() ) {
                scoreBoard.Score = scoreText
                scoreBoard.Name = nameText
                scoreBoard.Date = getDate()

                i("Added :  $scoreBoard")
            }
            else {
                i("WRONG WRONG WRONG")


                Snackbar
                    .make(it, "YOu gotta Enter Somthing", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun getDate(): String {
        val c: Date = Calendar.getInstance().time
        println("Current time => $c")

        val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        return df.format(c)
    }
}