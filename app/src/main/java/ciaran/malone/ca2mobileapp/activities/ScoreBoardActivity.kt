package ciaran.malone.ca2mobileapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ciaran.malone.ca2mobileapp.R
import ciaran.malone.ca2mobileapp.databinding.ActivityScoreBoardBinding
import ciaran.malone.ca2mobileapp.databinding.CardScoreboardBinding
import ciaran.malone.ca2mobileapp.main.MainApp
import ciaran.malone.ca2mobileapp.models.ScoreModel

private lateinit var binding: ActivityScoreBoardBinding
private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
lateinit var app: MainApp

class ScoreBoardActivity : AppCompatActivity(), ScoreListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ScoreAdaptor(app.scoreBoard.findAll(),this)

        loadScoreboard()
        registerRefreshCallback()

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

    override fun onScoreClick(score: ScoreModel) {
        val launcherIntent = Intent(this, ShowScoreActivity::class.java)
        launcherIntent.putExtra("score_edit", score)
        startActivityForResult(launcherIntent,0)
    }

    private fun loadScoreboard() {
        showScoreboard(app.scoreBoard.findAll())
    }

    private fun showScoreboard(scoreboard: List<ScoreModel>) {
        binding.recyclerView.adapter = ScoreAdaptor(scoreboard, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { loadScoreboard() }
    }
}

interface ScoreListener {
    fun onScoreClick(score: ScoreModel)
}

class ScoreAdaptor (private var scoreboard: List<ScoreModel>,
                   private val listener: ScoreListener
) :
    RecyclerView.Adapter<ScoreAdaptor.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardScoreboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val playerScore = scoreboard[holder.adapterPosition]
        holder.bind(playerScore, listener)
    }

    override fun getItemCount(): Int = scoreboard.size

    class MainHolder(private val binding: CardScoreboardBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(playerScore: ScoreModel, listener: ScoreListener) {
            binding.scoreText.text = playerScore.Score.toString()
            binding.nameText.text = playerScore.Name
            binding.textDate.text = playerScore.Date
            binding.textNumber.text = app.scoreBoard.findIndex(playerScore)
            binding.root.setOnClickListener { listener.onScoreClick(playerScore)}
        }
    }
}