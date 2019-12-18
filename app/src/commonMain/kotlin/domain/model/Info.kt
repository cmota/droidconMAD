package domain.model

import data.entities.SessionShortEntity
import data.entities.SpeakerShortEntity
import kotlinx.serialization.Serializable

@Serializable
data class Info(val id: String, val name: String)

fun SpeakerShortEntity.toInfo() = Info(
    id = id,
    name = name)

fun SessionShortEntity.toInfo() = Info(
    id = id,
    name = name)