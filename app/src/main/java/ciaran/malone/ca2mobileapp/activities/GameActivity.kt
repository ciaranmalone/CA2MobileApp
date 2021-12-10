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
import android.os.CountDownTimer
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import ciaran.malone.ca2mobileapp.databinding.ActivityGameBinding
import ciaran.malone.ca2mobileapp.main.MainApp
import ciaran.malone.ca2mobileapp.models.ScoreModel
import com.google.android.material.snackbar.Snackbar
import pl.droidsonroids.gif.GifImageView
import java.util.*
import timber.log.Timber.i


class GameActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityGameBinding
    private lateinit var sensorManager: SensorManager

    private lateinit var square: GifImageView
    private lateinit var textCounter: TextView
    private lateinit var TimeCounter: TextView


    var playerScore = ScoreModel()
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        square = binding.gifImage
        textCounter = binding.RotationCount
        TimeCounter = binding.TimerView
        setUpSensor()
        app = application as MainApp

        i("DEBUG_MESSAGE -> GAME STARED")

        binding.StartButton.setOnClickListener {
            if(!timerStarted) {
                timer.start()
                timerStarted = true
            }
        }

//        binding.submitScoreButton.setOnClickListener {
//            i("DEBUG_MESSAGE -> BUTTON PRESSED")
//
//            val scoreText = binding.scoreInputField.text.toString()
//            val nameText = binding.nameInputField.text.toString()
//            if (scoreText.isNotEmpty() and nameText.isNotEmpty() ) {
//                playerScore.Score = scoreText
//                playerScore.Name = nameText
//                playerScore.Date = getDate()
//
//                app.scoreBoard.add(playerScore.copy())
//
//                val intent = Intent(this, ScoreBoardActivity::class.java)
//                startActivity(intent)
//                i("DEBUG_MESSAGE -> Added :  $playerScore")
//            }
//            else {
//                i("DEBUG_MESSAGE -> WRONG WRONG WRONG")
//                Snackbar
//                    .make(it, "YOu gotta Enter Somthing", Snackbar.LENGTH_LONG)
//                    .show()
//            }
//        }
    }



    private fun setUpSensor(){
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)?.also {
            sensorManager.registerListener(this,
                it,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST)
        }
    }

    //rotation Logic
    var rotationCount = 0
    var rotationFlag1 = false; var rotationFlag2 = false;
    var rotationFlag3 = false; var rotationFlag4 = false;

    var timerStarted = false

    var startingXRotation = 0f;

    override fun onSensorChanged(event: SensorEvent?) {

        if (!timerStarted)
            return

        val xRotation = event?.values?.get(0);
        val yRotation = event?.values?.get(1);
        val zRotation = event?.values?.get(2);

        if (startingXRotation == 0f)
            startingXRotation = xRotation!!

        if (xRotation != null) {

            if (xRotation > 60 && xRotation < 90)
            {
                rotationFlag1 = true
                i("rotation1")
            }
            if (xRotation > 150 && xRotation < 180)
            {
                rotationFlag2 = true
                i("rotation2")
            }
            if (xRotation > 240 && xRotation < 270)
            {
                rotationFlag3 = true
                i("rotation3")
            }
            if (xRotation > 330 && xRotation < 360)
            {
                rotationFlag4 = true
                i("rotation1")
            }

            if (rotationFlag1 && rotationFlag2 && rotationFlag3 && rotationFlag4){
                rotationCount += 1
                rotationFlag1 = false
                rotationFlag2 = false
                rotationFlag3 = false
                rotationFlag4 = false
            }

            textCounter.text = "$rotationCount"
            square.apply {

                rotation = xRotation - startingXRotation
                rotationX = yRotation!!
                rotationY= zRotation!!

            }
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    //TIMER
    val timer = object: CountDownTimer(6000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            TimeCounter.text = (millisUntilFinished/1000).toString()
        }

        override fun onFinish() {
            val intent = Intent(this@GameActivity, ShowScoreActivity::class.java)
            intent.putExtra("score", rotationCount)
            startActivity(intent)
        }
    }
}