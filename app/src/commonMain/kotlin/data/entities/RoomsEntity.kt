package data.entities

import kotlinx.serialization.Serializable

@Serializable
data class RoomsEntity(
    val id: String,
    val name: String,
    val sessions: List<SessionEntity>?,
    val hasOnlyPlenumSessions: Boolean)