package ciaran.malone.ca2mobileapp.models

import timber.log.Timber.i
var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class ScoreboardMemStore: ScoreBoardStore {
    val scoreboard = ArrayList<ScoreModel>()

    override fun findAll(): List<ScoreModel> {
        return  scoreboard
    }

    override fun create(score: ScoreModel) {
        scoreboard.add(score)
        logAll()
    }

    override fun update(score: ScoreModel) {
        var foundScore: ScoreModel? = scoreboard.find { p -> p.id == score.id }
        if (foundScore != null) {
            foundScore.Name = score.Name
            logAll()
        }
    }

    override fun delete(score: ScoreModel) {
        var foundScore: ScoreModel? = scoreboard.find { p -> p.id == score.id }
        if (foundScore != null) {
            scoreboard.remove(foundScore)
            logAll()
        }
    }

    override fun findIndex(score: ScoreModel): String {
        TODO("Not yet implemented")
    }


    private fun logAll() {
        scoreboard.forEach{ i("$it")}
    }


}