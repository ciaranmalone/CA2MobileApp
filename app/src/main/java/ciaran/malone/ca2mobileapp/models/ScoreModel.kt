package ciaran.malone.ca2mobileapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScoreModel(var id: Long = 0,
                      var Score: String="",
                      var Name: String= "",
                      var Date: String =""): Parcelable
