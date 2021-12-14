package ciaran.malone.ca2mobileapp.models

import android.content.Context
import android.net.Uri
import ciaran.malone.ca2mobileapp.helpers.*
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

const val JSON_FILE ="scoreboard.json"

val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()

val listType: Type = object: TypeToken<ArrayList<ScoreModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class ScoreBoardJSONStore(private val context: Context) : ScoreBoardStore {

    var scoreboard = mutableListOf<ScoreModel>()

    init {
        if (exists(context, JSON_FILE)){
            deserialize()
        }
    }

    override fun findAll(): List<ScoreModel> {
        logAll()
        orderList()
        return scoreboard.sortedByDescending { it.Score }
    }

    override fun findIndex(score: ScoreModel): String {

<<<<<<< Updated upstream
        var count = scoreboard.indexOf(score)
        return "#$count+1"
=======
        var count = (scoreboard.sortedByDescending{ it.Score }.indexOf(score))+1
        return "#$count"
>>>>>>> Stashed changes
    }

    override fun create(score: ScoreModel) {
        score.id = generateRandomId()
        scoreboard.add(score)
        serialize()
    }

    override fun update(score: ScoreModel) {
        var foundScore: ScoreModel? = scoreboard.find { p -> p.id == score.id }
        if (foundScore != null) {
            foundScore.Name = score.Name
            logAll()
            serialize()
        }
    }

    override fun delete(score: ScoreModel) {
        scoreboard.remove(score)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(scoreboard, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        scoreboard = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        scoreboard.forEach { Timber.i("$it") }
    }

    private fun orderList(){
        scoreboard.sortedByDescending { it.Score }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}

