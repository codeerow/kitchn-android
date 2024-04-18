package com.spirit.kitchn.core.auth.repo

class TokenRepo(
    private val tokensManager: TokensManager
) {

    var accessToken: String
        get() = tokensManager.getAccess()
        set(value) {
            tokensManager.saveAccess(value)
        }
}