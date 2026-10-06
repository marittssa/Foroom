package com.example.foroom.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.design_system.R as DesignSystemR
import com.example.foroom.helper.WaitUtils
import org.hamcrest.Matchers.allOf

class ChangePasswordPage {

    private val passwordField = allOf(
        withId(DesignSystemR.id.inputEditText),
        isDescendantOfA(withId(R.id.passwordInput))
    )

    private val repeatPasswordField = allOf(
        withId(DesignSystemR.id.inputEditText),
        isDescendantOfA(withId(R.id.repeatPasswordInput))
    )

    fun enterPassword(password: String): ChangePasswordPage {

        WaitUtils.waitForView(passwordField)

        onView(passwordField)
            .perform(
                click(),
                replaceText(password),
                closeSoftKeyboard()
            )

        return this
    }

    fun repeatPassword(password: String): ChangePasswordPage {

        onView(repeatPasswordField)
            .perform(
                click(),
                replaceText(password),
                closeSoftKeyboard()
            )

        return this
    }

    fun clickConfirm(): ChangePasswordPage {

        WaitUtils.waitForView(
            withId(DesignSystemR.id.actionButton)
        )

        onView(withId(DesignSystemR.id.actionButton))
            .perform(click())

        return this
    }
}