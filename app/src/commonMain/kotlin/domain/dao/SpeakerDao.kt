package domain.dao

import data.ScheduleDb
import domain.model.Speaker

class SpeakerDao(database: ScheduleDb) {

    private val db = database.speakerModelQueries

    internal fun insertOrReplace(speaker: Speaker) {
        db.insertOrReplaceSpeaker(
            id = speaker.id,
            speaker = speaker)
    }

    internal fun getAllSpeakers(): List<Speaker> {
        val data = db.selectAllSpeakers().executeAsList()

        val speakers = mutableListOf<Speaker>()
        for (item in data) {
            speakers += item.speaker
        }

        return speakers
    }
}