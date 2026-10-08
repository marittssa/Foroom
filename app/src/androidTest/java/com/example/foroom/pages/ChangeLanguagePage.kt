package com.example.foroom.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.foroom.helper.WaitUtils

class ChangeLanguagePage {
    fun selectGeorgian(): ChangeLanguagePage {

        WaitUtils.waitForView(
            withId(R.id.languageButtonGeo)
        )

        onView(withId(R.id.languageButtonGeo))
            .perform(click())

        return this
    }

    fun selectEnglish(): ChangeLanguagePage {

        WaitUtils.waitForView(
            withId(R.id.languageButtonEng)
        )

        onView(withId(R.id.languageButtonEng))
            .perform(click())

        return this
    }
}