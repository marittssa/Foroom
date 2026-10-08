
package com.example.foroom.steps

import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.foroom.helper.WaitUtils
import com.example.foroom.pages.ConversationPage
import org.hamcrest.Matchers.anyOf

class ConversationSteps {

    private val conversationPage = ConversationPage()
    private val loginSteps = LoginSteps()
    private val profileSteps = ProfileSteps()

    fun signInAs(
        username: String,
        password: String
    ): ConversationSteps {

        WaitUtils.waitForView(
            anyOf(
                withId(R.id.logInButton),
                withId(R.id.homeNavigationProfile),
                withId(R.id.messagesRecyclerView)
            ),
            10000
        )

        if (conversationPage.isConversationOpen()) {
            conversationPage.closeChat()
        }

        if (!loginSteps.isLoginScreenDisplayed()) {
            profileSteps
                .openProfile()
                .signOut()

            WaitUtils.waitForView(
                withId(R.id.logInButton),
                10000
            )
        }

        loginSteps.login(username, password)

        WaitUtils.waitForView(
            withId(R.id.navBar),
            10000
        )

        loginSteps.verifyHomeScreen()

        return this
    }

    fun openChat(chatName: String): ConversationSteps {
        conversationPage
            .openChat(chatName)
            .verifyChatTitle(chatName)

        return this
    }

    fun sendAndVerify(text: String): ConversationSteps {
        conversationPage
            .sendMessage(text)
            .verifyMessageVisible(text)

        return this
    }

    fun verifyMessage(text: String): ConversationSteps {
        conversationPage.verifyMessageVisible(text)
        return this
    }

    fun verifyMessageAndSender(
        text: String,
        username: String
    ): ConversationSteps {
        conversationPage
            .verifyMessageVisible(text)
            .verifyMessageSender(text, username)

        return this
    }

    fun verifyMessageOutsideVisibleArea(
        text: String
    ): ConversationSteps {

        check(!conversationPage.isMessageVisible(text)) {
            "Message '$text' is still in the initial visible area"
        }

        return this
    }

    fun findOlderMessage(
        text: String,
        sender: String
    ): ConversationSteps {
        conversationPage.swipeToOlderMessage(text, sender)
        return this
    }

    fun findMessageAndVerifySender(
        text: String,
        sender: String
    ): ConversationSteps {
        conversationPage
            .scrollToMessage(text)
            .verifyMessageSender(text, sender)

        return this
    }

    fun closeChat(): ConversationSteps {
        conversationPage.closeChat()
        return this
    }

    fun signOut(): ConversationSteps {
        if (conversationPage.isConversationOpen()) {
            conversationPage.closeChat()
        }

        profileSteps
            .openProfile()
            .signOut()

        WaitUtils.waitForView(
            withId(R.id.logInButton),
            10000
        )

        loginSteps.verifyLoginPage()

        return this
    }
}
