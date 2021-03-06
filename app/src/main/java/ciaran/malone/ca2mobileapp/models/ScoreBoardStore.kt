package ciaran.malone.ca2mobileapp.models

interface ScoreBoardStore {
    fun findAll(): List<ScoreModel>
    fun create(score: ScoreModel)
    fun update(score: ScoreModel)
    fun delete(score: ScoreModel)
    fun findIndex(score: ScoreModel): String
}