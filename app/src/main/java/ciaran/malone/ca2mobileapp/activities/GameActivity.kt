package ciaran.malone.ca2mobileapp.activities
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ciaran.malone.ca2mobileapp.databinding.ActivityGameBinding
import ciaran.malone.ca2mobileapp.main.MainApp
import ciaran.malone.ca2mobileapp.models.ScoreModel
import com.google.android.material.snackbar.Snackbar
import java.util.*
import timber.log.Timber.i


class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding

    var playerScore = ScoreModel()
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        i("DEBUG_MESSAGE -> GAME STARED")

        binding.submitScoreButton.setOnClickListener {
            i("DEBUG_MESSAGE -> BUTTON PRESSED")

            val scoreText = binding.scoreInputField.text.toString()
            val nameText = binding.nameInputField.text.toString()
            if (scoreText.isNotEmpty() and nameText.isNotEmpty() ) {
                playerScore.Score = scoreText
                playerScore.Name = nameText
                playerScore.Date = getDate()

                app.scoreBoard.add(playerScore.copy())

                val intent = Intent(this, ScoreBoardActivity::class.java)
                startActivity(intent)
                i("DEBUG_MESSAGE -> Added :  $playerScore")
            }
            else {
                i("DEBUG_MESSAGE -> WRONG WRONG WRONG")
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