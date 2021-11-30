package ciaran.malone.ca2mobileapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ScoreAdaptor(app.scoreBoard)
    }
}

class ScoreAdaptor constructor(private var scoreboard: List<ScoreModel>) : RecyclerView.Adapter<ScoreAdaptor.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreAdaptor.MainHolder {
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