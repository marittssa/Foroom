package com.example.foroom.steps

import com.example.foroom.pages.LoginPage

class LoginSteps {

    private val loginPage = LoginPage()

    fun verifyLoginPage(): LoginSteps {
        loginPage.verifyLoginScreenDisplayed()
        return this
    }

    fun login(username: String, password: String): LoginSteps {
        loginPage.enterUsername(username)
        loginPage.enterPassword(password)
        loginPage.clickLogin()

        return this
    }

    fun verifyPasswordError(): LoginSteps {
        loginPage.verifyPasswordErrorDisplayed()
        return this
    }

    fun verifyUsernameError(): LoginSteps {
        loginPage.verifyUsernameErrorDisplayed()
        return this
    }

    fun openRegistration(): LoginSteps {
        loginPage.clickSignUp()
        return this
    }
}