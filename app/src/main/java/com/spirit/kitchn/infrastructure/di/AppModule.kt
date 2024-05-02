package com.spirit.kitchn.infrastructure.di

import com.spirit.kitchn.infrastructure.di.core.authModule
import com.spirit.kitchn.infrastructure.di.core.productModule
import com.spirit.kitchn.infrastructure.di.core.recipeModule
import com.spirit.kitchn.infrastructure.di.core.userModule

val appModule = uiModule +
    authModule +
    userModule +
    recipeModule +
    networkModule +
    productModule