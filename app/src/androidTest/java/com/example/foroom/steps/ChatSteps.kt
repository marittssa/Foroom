package com.example.foroom.steps

import com.example.foroom.pages.ChatsPage
import com.example.foroom.pages.CreateChatPage
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.foroom.helper.WaitUtils
import org.hamcrest.Matchers.allOf

class ChatSteps {

    private val createChatPage =
        CreateChatPage()

    private val chatsPage =
        ChatsPage()

    fun createChat(
        chatName: String
    ): ChatSteps {

        createChatPage
            .openCreateChat()
            .enterChatName(chatName)
            .selectFirstChatImage()
            .clickCreateChat()

        return this
    }

    fun verifyCreatedChatOpened(chatName: String): ChatSteps {

        val chatNameMatcher = allOf(
            withText(chatName),
            isDisplayed()
        )

        WaitUtils.waitForView(chatNameMatcher)

        onView(chatNameMatcher)
            .check(matches(isDisplayed()))

        return this
    }

    fun closeChat(): ChatSteps {

        chatsPage.closeChat()

        return this
    }

    fun searchForChat(
        chatName: String
    ): ChatSteps {

        chatsPage.searchForChat(chatName)

        return this
    }

    fun verifyChatInList(
        chatName: String
    ): ChatSteps {

        chatsPage.verifyChatInList(chatName)

        return this
    }
}