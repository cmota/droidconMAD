package presentation.cb

import domain.model.Speaker

interface ISpeakersData {

    fun onSpeakersDataFetched(speakers: List<Speaker>)

    fun onSpeakersDataFailed(e: Exception)
}