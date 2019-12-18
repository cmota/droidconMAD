package data.entities

import kotlinx.serialization.Serializable

/**
 *
 *  Sessions
 *
 *  {
 *      "groupId": 10663,
 *      "groupName": "Technical",
 *      "sessions": {
 *         ...
 *      }
 *  }
 */

@Serializable
data class SessionsEntity(
    val groupId: String?,
    val groupName: String,
    val sessions: List<SessionEntity>)
