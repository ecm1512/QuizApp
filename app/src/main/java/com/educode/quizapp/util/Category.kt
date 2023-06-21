package com.educode.quizapp.util

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category (
    val id: Int = 0,
    val name: String = "",
    val image: String = ""
): Parcelable


object ListCategories {
    val categories = listOf(
        Category(20,"Mythology","mythology"),
        Category(21,"Sports","sports"),
        Category(22,"Geography","geography"),
        Category(23,"History","history"),
        Category(24,"Politics","politics"),
        Category(25,"Art","arts"),
        Category(27,"Animals","animals"),
        Category(28,"Vehicles","vehicles"),
    )
}

fun Category.toDomainCategory() = com.educode.domain.models.Category(
    id,name, image
)

fun com.educode.domain.models.Category.toPresentationCategory() = Category(
    id,name,image
)

