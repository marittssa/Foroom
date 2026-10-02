package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.foroom.presentation.ui.activity.ForoomActivity

import com.example.foroom.steps.LoginSteps
import com.example.foroom.steps.RegistrationSteps
import org.junit.Rule
import org.junit.Test

class LoginAndRegistrationTests {

    @get:Rule
    val activityRule = ActivityScenarioRule(ForoomActivity::class.java)

    private val loginSteps = LoginSteps()
    private val registrationSteps = RegistrationSteps()

    @Test
    fun validUsernameAndInvalidPassword() {

        val validUsername = "ValidUsername"
        val invalidPassword = "WrongPassword123"

        loginSteps
            .verifyLoginPage()
            .login(
                username = validUsername,
                password = invalidPassword
            )
            .verifyPasswordError()
    }

    @Test
    fun invalidUsernameAndInvalidPassword() {

        val invalidUsername =
            "nonexistent_${System.currentTimeMillis()}"

        val invalidPassword =
            "WrongPassword123"

        loginSteps
            .verifyLoginPage()
            .login(
                username = invalidUsername,
                password = invalidPassword
            )
            .verifyUsernameError()
            .verifyPasswordError()
    }

    @Test
    fun successfulRegistration() {

        val uniqueUsername =
            "espresso_${System.currentTimeMillis()}"

        val validPassword =
            "Password123"

        loginSteps
            .verifyLoginPage()
            .openRegistration()

        registrationSteps
            .verifyRegistrationPage()
            .register(
                username = uniqueUsername,
                password = validPassword
            )
            .verifyRegistrationSuccessful()
    }
}