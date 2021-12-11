package ciaran.malone.ca2mobileapp.activities

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import ciaran.malone.ca2mobileapp.databinding.ActivityShowScoreBinding
import ciaran.malone.ca2mobileapp.main.MainApp
import ciaran.malone.ca2mobileapp.models.ScoreModel
import timber.log.Timber.i
import com.google.android.material.snackbar.Snackbar
import java.util.*

class ShowScoreActivity : AppCompatActivity() {
    private lateinit var textScore: TextView
    private lateinit var binding: ActivityShowScoreBinding

    private var playerScore = ScoreModel()
    lateinit var app : MainApp

    private var score = ""
    private var date = ""

    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = application as MainApp

        binding = ActivityShowScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textScore = binding.ScoreTextView


        if(intent.hasExtra("score_edit")) {
            edit = true
            playerScore = intent.extras?.getParcelable("score_edit")!!
            binding.nameTextField.setText(playerScore.Name)
            score = playerScore.Score
            date = playerScore.Date
        }
        else
        {
            score = intent.extras?.getInt("score").toString();
            date = getDate()
        }

        textScore.text = score

        binding.submitScoreButton.setOnClickListener {

            val nameText = binding.nameTextField.text.toString()

            if (nameText.isNotEmpty() ) {
                playerScore.Score = score
                playerScore.Name = nameText
                playerScore.Date = date

                if (edit) {
                    app.scoreBoard.update(playerScore.copy())
                } else {
                    app.scoreBoard.create(playerScore.copy())
                }

                i("DEBUG_MESSAGE -> Added :  $playerScore")

                val intent = Intent(this, ScoreBoardActivity::class.java)
                startActivity(intent)
                finish()
                i("DEBUG_MESSAGE -> Added :  $playerScore")
            }
            else {
                i("DEBUG_MESSAGE -> WRONG WRONG WRONG")
                Snackbar
                    .make(it, "Name Please", Snackbar.LENGTH_LONG)
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