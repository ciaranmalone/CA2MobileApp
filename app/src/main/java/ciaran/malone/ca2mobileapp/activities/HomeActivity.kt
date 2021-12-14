package ciaran.malone.ca2mobileapp.activities

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import ciaran.malone.ca2mobileapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager

    private lateinit var binding: ActivityHomeBinding
    private lateinit var playButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        playButton = binding.imageButton

        playButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

        binding.highscoreButton.setOnClickListener {
            val intent = Intent(this, ScoreBoardActivity::class.java)
            startActivity(intent)
        }
        setUpSensor()

    }

    //GEO SETUP
    private fun setUpSensor(){
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)?.also {
            sensorManager.registerListener(this,
                it,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST)
        }
    }
    var startingXRotation = 0f
    override fun onSensorChanged(event: SensorEvent?) {

        val xRotation = event?.values?.get(0);
        val yRotation = event?.values?.get(1);
        val zRotation = event?.values?.get(2);

        if (startingXRotation == 0f)
            startingXRotation = xRotation!!

        playButton.apply {

            if (xRotation != null) {
                rotation = xRotation - startingXRotation
            }
            rotationX = yRotation!!
            rotationY= zRotation!!

        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }
}