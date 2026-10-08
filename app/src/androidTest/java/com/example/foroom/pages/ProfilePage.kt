package com.example.foroom.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.foroom.helper.WaitUtils
import org.hamcrest.Matchers.allOf

class ProfilePage {
    fun openProfile(): ProfilePage {

        WaitUtils.waitForView(
            withId(R.id.homeNavigationProfile)
        )

        onView(withId(R.id.homeNavigationProfile))
            .perform(click())

        WaitUtils.waitForView(
            withId(R.id.changePasswordItem)
        )

        return this
    }

    fun clickChangePassword(): ProfilePage {

        onView(withId(R.id.changePasswordItem))
            .perform(click())

        return this
    }

    fun clickChangeLanguage(): ProfilePage {

        onView(withId(R.id.changeLanguageItem))
            .perform(click())

        return this
    }

    fun clickSignOut(): ProfilePage {

        onView(withId(R.id.signOutItem))
            .perform(click())

        return this
    }

    fun verifyGeorgianLanguage(): ProfilePage {

        val georgianText = allOf(
            withText("ენის შეცვლა"),
            isDisplayed()
        )

        WaitUtils.waitForView(georgianText)

        onView(georgianText)
            .check(matches(isDisplayed()))

        return this
    }

    fun verifyEnglishLanguage(): ProfilePage {

        val englishText = allOf(
            withText("Change Language"),
            isDisplayed()
        )

        WaitUtils.waitForView(englishText)

        onView(englishText)
            .check(matches(isDisplayed()))

        return this
    }
}