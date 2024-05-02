package com.spirit.kitchn.ui.component

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.onLongClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.spirit.kitchn.ui.theme.KTheme
import java.io.File
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive


sealed interface PhotoItem {
    val id: Int

    data class Photo(
        override val id: Int,
        val url: String,
    ) : PhotoItem

    data object AddPhotoItem : PhotoItem {
        override val id: Int = -1
    }
}

@Composable
fun PhotosGrid(
    photos: List<PhotoItem>,
    modifier: Modifier = Modifier,
    onDeleteAsset: (Int) -> Unit = {},
    onSetUri: (Uri) -> Unit = {},
) {
    val state = rememberLazyGridState()
    val autoScrollSpeed = remember { mutableStateOf(0f) }
    LaunchedEffect(autoScrollSpeed.value) {
        if (autoScrollSpeed.value != 0f) {
            while (isActive) {
                state.scrollBy(autoScrollSpeed.value)
                delay(10)
            }
        }
    }

    LazyVerticalGrid(
        state = state,
        columns = GridCells.Adaptive(minSize = 128.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp),
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        modifier = modifier.photoGridDragHandler(
            lazyGridState = state,
            autoScrollSpeed = autoScrollSpeed,
            autoScrollThreshold = with(LocalDensity.current) { 40.dp.toPx() }
        )
    ) {
        items(photos.toMutableList() + PhotoItem.AddPhotoItem, key = { it.id }) { photo ->
            when (photo) {
                PhotoItem.AddPhotoItem -> {
                    AddImageArea(
                        modifier = Modifier.aspectRatio(1f),
                        directory = File(LocalContext.current.cacheDir, "images"),
                        onSetUri = onSetUri,
                    )
                }

                is PhotoItem.Photo -> {
                    ImageItem(
                        photo = photo,
                        modifier = Modifier
                            .semantics {
                                onLongClick("Select") {
                                    onDeleteAsset(photo.id)
                                    true
                                }
                            }
                    )
                }
            }
        }
    }
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.photoGridDragHandler(
    lazyGridState: LazyGridState,
    autoScrollSpeed: MutableState<Float>,
    autoScrollThreshold: Float,
) = pointerInput(Unit) {

    var initialKey: Int? = null
    detectDragGesturesAfterLongPress(
        onDragCancel = { initialKey = null; autoScrollSpeed.value = 0f },
        onDragEnd = { initialKey = null; autoScrollSpeed.value = 0f },
        onDrag = { change, _ ->
            if (initialKey != null) {
                val distFromBottom =
                    lazyGridState.layoutInfo.viewportSize.height - change.position.y
                val distFromTop = change.position.y
                autoScrollSpeed.value = when {
                    distFromBottom < autoScrollThreshold -> autoScrollThreshold - distFromBottom
                    distFromTop < autoScrollThreshold -> -(autoScrollThreshold - distFromTop)
                    else -> 0f
                }
            }
        }
    )
}

@Composable
fun ImageItem(
    photo: PhotoItem.Photo,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        modifier = modifier.aspectRatio(1f),
        tonalElevation = 3.dp
    ) {
        Box {
            Image(
                painter = rememberAsyncImagePainter(photo.url),
                contentDescription = null,
                modifier = Modifier
                    .matchParentSize()
            )
        }
    }
}

@Preview
@Composable
private fun MyApp() {
    KTheme {
        PhotosGrid(
            photos = List(3) {
                PhotoItem.Photo(
                    id = it,
                    url = "https://picsum.photos/seed/${(0..100000).random()}/256/256"
                )
            } + PhotoItem.AddPhotoItem,
        )
    }
}