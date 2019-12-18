package data.entities

import kotlinx.serialization.Serializable

@Serializable
data class RoomEntity(
    val id: String,
    val name: String,
    val session: SessionEntity,
    val index: Int)