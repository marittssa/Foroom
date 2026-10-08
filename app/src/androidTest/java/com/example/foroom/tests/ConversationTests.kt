package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.steps.ConversationSteps
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConversationTests {

    @get:Rule
    val activityRule =
        ActivityScenarioRule(ForoomActivity::class.java)

    private val conversationSteps = ConversationSteps();

    companion object {
        private const val USER_A = "androidusera4"
        private const val PASSWORD_A = "Android4PassA123!"

        private const val USER_B = "androiduserb4"
        private const val PASSWORD_B = "Android4PassB123!"

        private const val JOHN_CHAT = "johnWeek"

        private const val OWN_CHAT = "Maritsa Machaidze 097272"

        private const val SHARED_CHAT = "something"
    }

    @Test
    fun sendMessageInJohnWeekAndVerifyAfterReopening() {

        val message =
            "let's go for a drink ${System.currentTimeMillis()}"

        conversationSteps
            .signInAs(USER_A, PASSWORD_A)
            .openChat(JOHN_CHAT)
            .sendAndVerify(message)
            .closeChat()
            .openChat(JOHN_CHAT)
            .verifyMessage(message)
            .signOut()
    }

    @Test
    fun sendAutomationAcademyQuestionInOwnChat() {

        val question =
            "Which module do you like most in the " +
                    "Automation Academy? ${System.currentTimeMillis()}"

        conversationSteps
            .signInAs(USER_A, PASSWORD_A)
            .openChat(OWN_CHAT)
            .sendAndVerify(question)
            .signOut()
    }

    @Test
    fun continueConversationBetweenTwoAccounts() {

        val runId = System.currentTimeMillis().toString()

        val greeting = "Hello from User A $runId"
        val reply = "Hello from User B $runId"

        conversationSteps
            .signInAs(USER_A, PASSWORD_A)
            .openChat(SHARED_CHAT)
            .sendAndVerify(greeting)
            .verifyMessageAndSender(greeting, USER_A)

        repeat(25) { index ->
            val extraMessage =
                "Additional message ${index + 1} - $runId"

            conversationSteps.sendAndVerify(extraMessage)
        }

        conversationSteps
            .verifyMessageOutsideVisibleArea(greeting)
            .closeChat()

        conversationSteps
            .signInAs(USER_B, PASSWORD_B)
            .openChat(SHARED_CHAT)
            .verifyMessageOutsideVisibleArea(greeting)
            .findOlderMessage(greeting, USER_A)
            .sendAndVerify(reply)
            .verifyMessageAndSender(reply, USER_B)
            .closeChat()

        conversationSteps
            .signInAs(USER_A, PASSWORD_A)
            .openChat(SHARED_CHAT)
            .findMessageAndVerifySender(reply, USER_B)
            .signOut()
    }
}