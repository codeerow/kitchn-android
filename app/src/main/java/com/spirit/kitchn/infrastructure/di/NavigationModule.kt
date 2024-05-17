package com.spirit.kitchn.infrastructure.di

import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.binds
import org.koin.dsl.module

val navigationModule = module {
    single {
        NavHostController(androidContext()).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
            navigatorProvider.addNavigator(DialogNavigator())
        }
    }.binds(arrayOf(NavHostController::class, NavController::class))
}
