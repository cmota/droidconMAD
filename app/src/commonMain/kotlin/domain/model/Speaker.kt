package domain.model

import data.entities.SessionShortEntity
import data.entities.SpeakerEntity
import kotlinx.serialization.Serializable

@Serializable
data class Speaker (
    val id: String,
    val fullName: String,
    val bio: String,
    val tagLine: String,
    val profilePicture: String,
    val sessions: List<Info>,
    val categories: List<String>)

fun SpeakerEntity.toSpeaker() = Speaker(
    id = id,
    fullName = fullName,
    bio = bio,
    tagLine = tagLine,
    profilePicture =  profilePicture ?: "",
    sessions = getSessions(sessions),
    categories = categories)

private fun getSessions(sessions: List<SessionShortEntity>): List<Info> {
    val info = mutableListOf<Info>()
    for (session in sessions) {
        info += session.toInfo()
    }

    return info
}