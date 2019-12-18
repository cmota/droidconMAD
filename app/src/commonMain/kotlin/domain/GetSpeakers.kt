package domain

import data.SessionizeAPI
import domain.model.Speaker
import domain.dao.SpeakerDao
import domain.model.toSpeaker
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class GetSpeakers(private val sessionizeAPI: SessionizeAPI, private val speakerDao: SpeakerDao) {

    suspend operator fun invoke(onSuccess: (List<Speaker>) -> Unit, onFailure: (Exception) -> Unit) {
        try {
            val result = sessionizeAPI.fetchSpeakers()

            coroutineScope {
                launch(uiDispatcher) {
                    val speakers = mutableListOf<Speaker>()
                    for (Speaker in result) {
                        val speaker = Speaker.toSpeaker()
                        speakers += speaker
                        speakerDao.insertOrReplace(speaker)
                    }

                    AppData.speakers = speakers
                    onSuccess(speakers)
                }
            }
        } catch (e: Exception) {
            coroutineScope {
                launch(uiDispatcher) {
                    val speakers = speakerDao.getAllSpeakers()
                    if (speakers.isNullOrEmpty()) {
                        onFailure(e)
                    } else {
                        AppData.speakers = speakers
                        onSuccess(speakers)
                    }
                }
            }
        }
    }
}
