package com.spirit.kitchn.ui.screen.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spirit.kitchn.BuildConfig
import com.spirit.kitchn.core.auth.LoginUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    val email = MutableStateFlow(if (BuildConfig.DEBUG) "roman.pozdniakov.98@gmail.com" else "")
    val password = MutableStateFlow(if (BuildConfig.DEBUG) "password" else "")

    val onNavigateNext = MutableSharedFlow<Unit>()

    fun onLoginClicked() {
        viewModelScope.launch {
            loginUseCase.execute(email = email.value, password = password.value)
            onNavigateNext.emit(Unit)
        }
    }
}