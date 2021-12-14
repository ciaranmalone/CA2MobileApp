package ciaran.malone.ca2mobileapp.activities

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import ciaran.malone.ca2mobileapp.databinding.ActivityShowScoreBinding
import ciaran.malone.ca2mobileapp.helpers.SoundPlayer
import ciaran.malone.ca2mobileapp.helpers.SoundPlayer.playApplauseSound
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

    private var score = 0
    private var date = ""

    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = application as MainApp

        binding = ActivityShowScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textScore = binding.ScoreTextView

        binding.deleteButton.visibility = View.INVISIBLE
        binding.editText.visibility = View.INVISIBLE

        if(intent.hasExtra("score_edit")) {
            edit = true
            playerScore = intent.extras?.getParcelable("score_edit")!!
            binding.nameTextField.setText(playerScore.Name)
            score = playerScore.Score as Int
            date = playerScore.Date

            binding.deleteButton.visibility = View.VISIBLE
            binding.editText.visibility = View.VISIBLE
        }
        else
        {
            playApplauseSound()
            score = intent.extras?.getInt("score")!!;
            date = getDate()
        }

        binding.deleteButton.setOnClickListener {
            app.scoreBoard.delete(playerScore)

            val intent = Intent(this, ScoreBoardActivity::class.java)
            startActivity(intent)
            finish()
        }

        textScore.text = score.toString()

        binding.submitScoreButton.setOnClickListener {

            val nameText = binding.nameTextField.text.toString()

            if (nameText.isNotEmpty() or (nameText.length < 8)) {
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
            else if (nameText.length < 12) {
                Snackbar
                    .make(it, "NAME TOO LONG, 8 CHARS MAX", Snackbar.LENGTH_LONG)
                    .show()
            }
            else
            {
                Snackbar
                    .make(it, "NAME IS NEEDED", Snackbar.LENGTH_LONG)
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