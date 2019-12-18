package data.entities

import kotlinx.serialization.Serializable

@Serializable
data class CategoryEntity(
    val id: String,
    val name: String,
    val categoryItems: List<CategoryItemsEntity>?,
    val sort: String)