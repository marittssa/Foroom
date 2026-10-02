package com.example.foroom.steps

import com.example.foroom.pages.RegistrationPage

class RegistrationSteps {

    private val registrationPage = RegistrationPage()

    fun verifyRegistrationPage(): RegistrationSteps {
        registrationPage.verifyRegistrationScreenDisplayed()
        return this
    }

    fun register(
        username: String,
        password: String
    ): RegistrationSteps {

        registrationPage.enterUsername(username)
        registrationPage.enterPassword(password)
        registrationPage.enterRepeatPassword(password)
        registrationPage.selectAvatar()
        registrationPage.clickSignUp()

        return this
    }

    fun verifyRegistrationSuccessful(): RegistrationSteps {
        registrationPage.verifyHomeScreenDisplayed()
        return this
    }
}