package domain.model

import data.entities.CategoryEntity
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: String,
    val name: String,
    val sort: String)

fun CategoryEntity.toCategory() = Category(
    id = id,
    name = name,
    sort = sort)