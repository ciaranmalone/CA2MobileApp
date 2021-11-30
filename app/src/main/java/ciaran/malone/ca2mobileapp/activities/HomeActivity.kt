package ciaran.malone.ca2mobileapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ciaran.malone.ca2mobileapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle("Sonic fan app")

        binding.imageButton.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
        }
    }
}