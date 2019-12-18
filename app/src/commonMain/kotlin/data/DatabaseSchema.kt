package data

import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.db.SqlDriver
import data.model.ScheduleModel
import data.model.SessionModel
import data.model.SpeakerModel
import domain.model.Schedule
import domain.model.Session
import domain.model.Speaker
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

fun createDatabase(driver: SqlDriver): ScheduleDb {
    val speakerAdapter = object : ColumnAdapter<Speaker, String> {

        override fun decode(databaseValue: String): Speaker {
            val json = Json(JsonConfiguration.Stable)
            return json.parse(Speaker.serializer(), databaseValue)
        }

        override fun encode(value: Speaker): String {
            val json = Json(JsonConfiguration.Stable)
            return json.stringify(Speaker.serializer(), value)
        }
    }

    val sessionAdapter = object : ColumnAdapter<Session, String> {

        override fun decode(databaseValue: String): Session {
            val json = Json(JsonConfiguration.Stable)
            return json.parse(Session.serializer(), databaseValue)
        }

        override fun encode(value: Session): String {
            val json = Json(JsonConfiguration.Stable)
            return json.stringify(Session.serializer(), value)
        }
    }

    val scheduleAdapter = object : ColumnAdapter<Schedule, String> {

        override fun decode(databaseValue: String): Schedule {
            val json = Json(JsonConfiguration.Stable)
            return json.parse(Schedule.serializer(), databaseValue)
        }

        override fun encode(value: Schedule): String {
            val json = Json(JsonConfiguration.Stable)
            return json.stringify(Schedule.serializer(), value)
        }
    }

    return ScheduleDb(
        driver = driver,
        SpeakerModelAdapter = SpeakerModel.Adapter(
            speakerAdapter = speakerAdapter
        ),

        SessionModelAdapter = SessionModel.Adapter(
            sessionAdapter = sessionAdapter
        ),

        ScheduleModelAdapter = ScheduleModel.Adapter(
            scheduleAdapter = scheduleAdapter
        )
    )
}