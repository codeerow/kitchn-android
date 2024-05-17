package com.spirit.kitchn.ui.screen.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.spirit.kitchn.BuildConfig
import com.spirit.kitchn.core.auth.LoginUseCase
import com.spirit.kitchn.infrastructure.navigation.HOME_ROUTE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val loginUseCase: LoginUseCase,
    private val navHostController: NavHostController,
) : ViewModel() {

    val email = MutableStateFlow(if (BuildConfig.DEBUG) "roman.pozdniakov.98@gmail.com" else "")
    val password = MutableStateFlow(if (BuildConfig.DEBUG) "password" else "")

    fun onLoginClicked() {
        viewModelScope.launch {
            loginUseCase.execute(email = email.value, password = password.value)
            navHostController.navigate(HOME_ROUTE)
        }
    }
}