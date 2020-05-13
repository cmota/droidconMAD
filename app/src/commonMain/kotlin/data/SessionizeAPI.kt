package data

import data.entities.ScheduleEntity
import data.entities.SessionsEntity
import data.entities.SpeakerEntity
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readText
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

private const val BASE_URL = "https://sessionize.com/api/v2/vf6y0n6t/view/"
private const val ENDPOINT_SESSIONS = "sessions"
private const val ENDPOINT_SPEAKERS = "speakers"
private const val ENDPOINT_SCHEDULE = "gridsmart"

@Suppress("EXPERIMENTAL_API_USAGE")
class SessionizeAPI(engine: HttpClientEngine) {

    private val client = HttpClient(engine) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    private val jsonParser = Json(JsonConfiguration.Stable.copy(ignoreUnknownKeys = true, isLenient = true))

    suspend fun fetchSessions(): List<SessionsEntity> {
        val response = client.get<HttpResponse> {
            url ("$BASE_URL$ENDPOINT_SESSIONS")
        }

        val jsonBody = response.readText()
        return jsonParser.parse(SessionsEntity.serializer().list, jsonBody)
    }

    suspend fun fetchSpeakers(): List<SpeakerEntity> {
        val response = client.get<HttpResponse> {
            url ("$BASE_URL$ENDPOINT_SPEAKERS")
        }

        val jsonBody = response.readText()
        return jsonParser.parse(SpeakerEntity.serializer().list, jsonBody)
    }

    suspend fun fetchSchedule(): List<ScheduleEntity> {
        val response = client.get<HttpResponse> {
            url ("$BASE_URL$ENDPOINT_SCHEDULE")
        }

        val jsonBody = response.readText()
        return jsonParser.parse(ScheduleEntity.serializer().list, jsonBody)
    }
}