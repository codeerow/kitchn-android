package com.spirit.kitchn.ui.component.photos_grid

sealed interface PhotoItem {
    val id: Int?

    data class Photo(
        override val id: Int? = null,
        val url: String,
    ) : PhotoItem

    data object AddPhotoItem : PhotoItem {
        override val id: Int? = null
    }
}
