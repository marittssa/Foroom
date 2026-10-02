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
import androidx.test.espresso.matcher.ViewMatchers.isClickable
import org.hamcrest.Matchers.allOf
import android.view.View
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import com.example.design_system.R as DesignSystemR

class RegistrationPage {

    private val usernameField = allOf(
        withId(DesignSystemR.id.inputEditText),
        isDescendantOfA(withId(R.id.userNameInput))
    )

    private val passwordField = allOf(
        withId(DesignSystemR.id.inputEditText),
        isDescendantOfA(withId(R.id.passwordInput))
    )

    private val repeatPasswordField = allOf(
        withId(DesignSystemR.id.inputEditText),
        isDescendantOfA(withId(R.id.repeatPasswordInput))
    )

    fun verifyRegistrationScreenDisplayed() {
        onView(withId(R.id.userNameInput))
            .check(matches(isDisplayed()))

        onView(withId(R.id.passwordInput))
            .check(matches(isDisplayed()))

        onView(withId(R.id.repeatPasswordInput))
            .check(matches(isDisplayed()))

        onView(withId(R.id.listView))
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

    fun enterRepeatPassword(password: String) {
        onView(repeatPasswordField)
            .perform(
                replaceText(password),
                closeSoftKeyboard()
            )
    }

    fun selectAvatar() {
        onView(
            first(
                allOf(
                    isDescendantOfA(withId(R.id.listView)),
                    isClickable()
                )
            )
        ).perform(click())
    }

    fun clickSignUp() {
        onView(withId(R.id.signUpButton))
            .perform(click())
    }

    fun verifyHomeScreenDisplayed() {
        onView(withId(R.id.navBar))
            .check(matches(isDisplayed()))
    }

    private fun first(matcher: Matcher<View>): Matcher<View> {
        return object : TypeSafeMatcher<View>() {

            private var matched = false

            override fun describeTo(description: Description) {
                description.appendText("first matching view")
                matcher.describeTo(description)
            }

            override fun matchesSafely(view: View): Boolean {
                if (matched) {
                    return false
                }

                if (matcher.matches(view)) {
                    matched = true
                    return true
                }

                return false
            }
        }
    }
}