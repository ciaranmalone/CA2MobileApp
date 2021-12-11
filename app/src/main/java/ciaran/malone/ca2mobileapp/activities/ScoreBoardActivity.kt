package ciaran.malone.ca2mobileapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ciaran.malone.ca2mobileapp.R
import ciaran.malone.ca2mobileapp.databinding.ActivityScoreBoardBinding
import ciaran.malone.ca2mobileapp.databinding.CardScoreboardBinding
import ciaran.malone.ca2mobileapp.main.MainApp
import ciaran.malone.ca2mobileapp.models.ScoreModel

private lateinit var binding: ActivityScoreBoardBinding

class ScoreBoardActivity : AppCompatActivity() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = title

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ScoreAdaptor(app.scoreBoard)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, HomeActivity::class.java)
                startActivityForResult(launcherIntent,0)
            }
        }
        finish();

        return super.onOptionsItemSelected(item)
    }
}

class ScoreAdaptor constructor(private var scoreboard: List<ScoreModel>) : RecyclerView.Adapter<ScoreAdaptor.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardScoreboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val playerScore = scoreboard[holder.adapterPosition]
        holder.bind(playerScore)
    }

    override fun getItemCount(): Int = scoreboard.size

    class MainHolder(private val binding: CardScoreboardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(playerScore: ScoreModel) {
            binding.playerScore.text = playerScore.Score
            binding.playerName.text = playerScore.Name

        }
    }
}