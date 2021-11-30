package ciaran.malone.ca2mobileapp.main

import android.app.Application
import ciaran.malone.ca2mobileapp.models.ScoreModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp: Application() {

    val scoreBoard = ArrayList<ScoreModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("DEBUG_MESSAGE-> APP HAS STARTED")
        scoreBoard.add(ScoreModel("12", "CIA"))
        scoreBoard.add(ScoreModel("2", "RAN"))
        scoreBoard.add(ScoreModel("3", "JAC"))
    }
}