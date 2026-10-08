package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.design_system.R as DesignSystemR
import com.example.foroom.helper.WaitUtils
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class ChatsPage {
    private val searchField = allOf(
        withId(DesignSystemR.id.inputEditText),
        isDescendantOfA(withId(R.id.searchChatInput))
    )

    fun verifyCreatedChatOpened(chatName: String): ChatsPage {

        val matcher = allOf(
            withId(DesignSystemR.id.chatTitleTextView),
            withText(chatName)
        )

        WaitUtils.waitForView(matcher)

        onView(matcher)
            .check(matches(isDisplayed()))

        return this
    }

    fun closeChat(): ChatsPage {

        onView(withId(R.id.closeButton))
            .perform(click())

        WaitUtils.waitForView(
            withId(R.id.chatsRecyclerView)
        )

        return this
    }

    fun searchForChat(chatName: String): ChatsPage {

        WaitUtils.waitForView(searchField)

        onView(searchField)
            .perform(
                click(),
                replaceText(chatName),
                closeSoftKeyboard()
            )

        return this
    }

    fun verifyChatInList(chatName: String): ChatsPage {

        val chatMatcher = chatInsideRecyclerView(chatName)

        WaitUtils.waitForView(chatMatcher)

        onView(chatMatcher)
            .check(matches(isDisplayed()))

        return this
    }

    private fun chatInsideRecyclerView(
        chatName: String
    ): Matcher<View> {

        return allOf(
            withId(DesignSystemR.id.chatTitleTextView),
            withText(chatName),
            isDescendantOfA(
                withId(R.id.chatsRecyclerView)
            )
        )
    }
}