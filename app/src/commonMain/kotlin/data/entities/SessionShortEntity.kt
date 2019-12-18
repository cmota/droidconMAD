package data.entities

import kotlinx.serialization.Serializable

/**
 *   Speaker.sessions
 *
 *   "speakers": [
 *      {
 *      "id": 131513,
 *      "name": "The Hitchhikers Guide Through Kotlin Multiplatform"
 *      }
 */

@Serializable
data class SessionShortEntity(val id: String, val name: String)