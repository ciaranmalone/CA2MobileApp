package ciaran.malone.ca2mobileapp.main

import android.app.Application
import ciaran.malone.ca2mobileapp.models.ScoreModel
import ciaran.malone.ca2mobileapp.models.ScoreboardMemStore
import ciaran.malone.ca2mobileapp.models.ScoreBoardStore
import ciaran.malone.ca2mobileapp.models.ScoreBoardJSONStore

import timber.log.Timber
import timber.log.Timber.i

class MainApp: Application() {

    lateinit var scoreBoard: ScoreBoardStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        scoreBoard = ScoreBoardJSONStore(applicationContext)
    }
}