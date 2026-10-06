package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.design_system.R as DesignSystemR
import com.example.design_system.components.image_chooser.ImageChooserItemView
import com.example.foroom.helper.WaitUtils
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher

class CreateChatPage {

    private val chatNameField: Matcher<View> = allOf(
        withId(DesignSystemR.id.inputEditText),
        isDescendantOfA(
            withId(R.id.chatNameInput)
        )
    )

    fun openCreateChat(): CreateChatPage {

        WaitUtils.waitForView(
            withId(R.id.homeNavigationCreateChat)
        )

        onView(
            withId(R.id.homeNavigationCreateChat)
        ).perform(click())

        WaitUtils.waitForView(chatNameField)

        return this
    }

    fun enterChatName(
        chatName: String
    ): CreateChatPage {

        onView(chatNameField)
            .perform(
                click(),
                replaceText(chatName),
                closeSoftKeyboard()
            )

        return this
    }

    fun selectFirstChatImage(): CreateChatPage {

        val imageMatcher: Matcher<View> = allOf(
            isAssignableFrom(
                ImageChooserItemView::class.java
            ),
            isDescendantOfA(
                withId(R.id.chatImageChooser)
            ),
            isDisplayed()
        )

        WaitUtils.waitForView(imageMatcher)

        onView(
            first(imageMatcher)
        ).perform(click())

        return this
    }

    fun clickCreateChat(): CreateChatPage {

        WaitUtils.waitForView(
            withId(R.id.createChatButton)
        )

        onView(
            withId(R.id.createChatButton)
        ).perform(click())

        return this
    }

    private fun first(
        matcher: Matcher<View>
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {

            private var hasMatched = false

            override fun describeTo(
                description: Description
            ) {
                description.appendText(
                    "first view matching: "
                )

                matcher.describeTo(description)
            }

            override fun matchesSafely(
                view: View
            ): Boolean {

                if (hasMatched) {
                    return false
                }

                if (matcher.matches(view)) {
                    hasMatched = true
                    return true
                }

                return false
            }
        }
    }
}