package ciaran.malone.ca2mobileapp.activities
import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import ciaran.malone.ca2mobileapp.databinding.ActivityGameBinding
import ciaran.malone.ca2mobileapp.main.MainApp
import ciaran.malone.ca2mobileapp.models.ScoreModel
import com.google.android.material.snackbar.Snackbar
import java.util.*
import timber.log.Timber.i


class GameActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityGameBinding
    private lateinit var sensorManager: SensorManager
    private lateinit var square: TextView

    var playerScore = ScoreModel()
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        square = binding.speenSquare
        setUpSensor()
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

    private fun setUpSensor(){
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also {
            sensorManager.registerListener(this,
                it,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event?.sensor?.type == Sensor.TYPE_ACCELEROMETER){
            val sides = event.values[0]
            val upDown = event.values[1]

            square.apply {
                rotationX = upDown * 3f
                rotationY = sides * 3f
                rotation = -sides
                translationX = sides * -10
                translationY = upDown * 10
            }

            val color = if (upDown.toInt() == 0 && sides.toInt() ==0) Color.GREEN else Color.RED
            square.setBackgroundColor(color)

            square.text = "u/down ${upDown.toInt()}\nleft/right ${sides.toInt()}"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    override fun onDestroy() {
        sensorManager.unregisterListener(this)
        super.onDestroy()
    }
}