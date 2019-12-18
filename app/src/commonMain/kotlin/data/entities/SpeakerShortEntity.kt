package data.entities

import kotlinx.serialization.Serializable

/**
 *   Speaker.sessions
 *
 *   "sessions": [
 *      {
 *      "id": 131513,
 *      "name": "The Hitchhikers Guide Through Kotlin Multiplatform"
 *      }
 */

@Serializable
data class SpeakerShortEntity(val id: String, val name: String)