package domain.model

import data.entities.CategoryEntity
import data.entities.SessionEntity
import data.entities.SpeakerShortEntity
import kotlinx.serialization.Serializable

@Serializable
data class Session (val id: String,
                    val title: String,
                    val summary: String?,
                    val startsAt: String?,
                    val endsAt: String?,
                    val speakers: List<Info>,
                    val categories: List<Category>,
                    val room: String?,
                    var favourite: Boolean = false)

fun SessionEntity.toSession() = Session(
    id = id,
    title = title,
    summary = description,
    startsAt = startsAt,
    endsAt =  endsAt,
    speakers = getSpeakers(speakers),
    categories = getCategories(categories),
    room = room)

private fun getSpeakers(speakers: List<SpeakerShortEntity>?): List<Info> {
    val info = mutableListOf<Info>()
    if (speakers != null) {
        for (speaker in speakers) {
            info += speaker.toInfo()
        }
    }

    return info
}

private fun getCategories(categories: List<CategoryEntity>?): List<Category> {
    val categoriesList = mutableListOf<Category>()
    if (categories != null) {
        for (category in categories) {
            categoriesList += category.toCategory()
        }
    }

    return categoriesList
}