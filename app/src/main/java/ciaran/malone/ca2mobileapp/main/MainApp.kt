package ciaran.malone.ca2mobileapp.main

import android.app.Application
import ciaran.malone.ca2mobileapp.models.ScoreModel
import ciaran.malone.ca2mobileapp.models.ScoreboardMemStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp: Application() {

    val scoreBoard = ScoreboardMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}