package com.spirit.kitchn.ui.screen.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spirit.kitchn.core.product.GetProductDetailsUseCase
import com.spirit.kitchn.core.product.model.ProductDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    private val productIdArg: String,
    getProductDetailsUseCase: GetProductDetailsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Loading)
    val state: StateFlow<State> = _state

    init {
        viewModelScope.launch {
            val content = getProductDetailsUseCase.execute(productIdArg).toContent()
            _state.emit(content)
        }
    }

    private fun ProductDTO.toContent(): State.Content {
        return State.Content(
            title = this.name,
            headerImageUrl = imageUrl,
            description = "TBD",
        )
    }

    sealed class State {
        abstract val title: String

        data object Loading : State() {
            override val title: String = "Loading"
        }

        data class Content(
            override val title: String,
            val headerImageUrl: String?,
            val description: String,
        ) : State()
    }
}