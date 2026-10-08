package com.example.foroom.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import org.hamcrest.Matchers.allOf
import com.example.design_system.R as DesignSystemR

class LoginPage {

    private val usernameField = allOf(
        withId(DesignSystemR.id.inputEditText),
        isDescendantOfA(withId(R.id.userNameInput))
    )

    private val passwordField = allOf(
        withId(DesignSystemR.id.inputEditText),
        isDescendantOfA(withId(R.id.passwordInput))
    )

    private val usernameDescription = allOf(
        withId(DesignSystemR.id.descriptionTextView),
        isDescendantOfA(withId(R.id.userNameInput))
    )

    private val passwordDescription = allOf(
        withId(DesignSystemR.id.descriptionTextView),
        isDescendantOfA(withId(R.id.passwordInput))
    )

    fun verifyLoginScreenDisplayed() {
        onView(withId(R.id.userNameInput))
            .check(matches(isDisplayed()))

        onView(withId(R.id.passwordInput))
            .check(matches(isDisplayed()))

        onView(withId(R.id.logInButton))
            .check(matches(isDisplayed()))
    }

    fun enterUsername(username: String) {
        onView(usernameField)
            .perform(
                replaceText(username),
                closeSoftKeyboard()
            )
    }

    fun enterPassword(password: String) {
        onView(passwordField)
            .perform(
                replaceText(password),
                closeSoftKeyboard()
            )
    }

    fun clickLogin() {
        onView(withId(R.id.logInButton))
            .perform(click())
    }

    fun clickSignUp() {
        onView(withId(R.id.signUpButton))
            .perform(click())
    }

    fun verifyUsernameErrorDisplayed() {
        onView(usernameDescription)
            .check(matches(isDisplayed()))
    }

    fun verifyPasswordErrorDisplayed() {
        onView(passwordDescription)
            .check(matches(isDisplayed()))
    }

    // NEW FOR HOMEWORK 3
    fun verifyHomeScreenDisplayed() {
        onView(withId(R.id.navBar))
            .check(matches(isDisplayed()))
    }

    fun isLoginScreenDisplayed(): Boolean {
        return try {
            onView(withId(R.id.logInButton))
                .check(matches(isDisplayed()))

            true
        } catch (e: Exception) {
            false
        }
    }
}