package com.example.foroom.steps

import com.example.foroom.pages.LoginPage

class LoginSteps {

    private val loginPage = LoginPage()

    fun login(
        username: String,
        password: String
    ): LoginSteps {

        loginPage.enterUsername(username)
        loginPage.enterPassword(password)
        loginPage.clickLogin()

        return this
    }

    fun verifyLoginPage(): LoginSteps {

        loginPage.verifyLoginScreenDisplayed()

        return this
    }

    fun verifyHomeScreen(): LoginSteps {

        loginPage.verifyHomeScreenDisplayed()

        return this
    }

    fun isLoginScreenDisplayed(): Boolean {
        return loginPage.isLoginScreenDisplayed()
    }
}