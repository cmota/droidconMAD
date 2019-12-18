package data.entities

import kotlinx.serialization.Serializable

@Serializable
data class LinksEntity(
    val title: String,
    val url: String,
    val linkType: String)