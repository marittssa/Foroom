
package com.example.foroom.pages

import android.graphics.Rect
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.foroom.helper.WaitUtils
import com.example.foroom.helper.swiper
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import com.example.design_system.R as DesignSystemR

class ConversationPage {

    private val chatsPage = ChatsPage()

    // -------------------------
    // MATCHERS
    // -------------------------

    private val messageInput: Matcher<View> = allOf(
        withId(DesignSystemR.id.inputEditText),
        isDescendantOfA(withId(R.id.messageInput))
    )

    private fun messageMatcher(text: String): Matcher<View> {
        return allOf(
            withId(DesignSystemR.id.messageTextView),
            withText(text),
            isDescendantOfA(
                withId(R.id.messagesRecyclerView)
            )
        )
    }

    private fun chatTitleMatcher(chatName: String): Matcher<View> {
        return allOf(
            withId(DesignSystemR.id.chatTitleTextView),
            withText(chatName)
        )
    }

    // -------------------------
    // CONVERSATION STATE
    // -------------------------

    fun isConversationOpen(): Boolean {
        return try {
            onView(withId(R.id.messagesRecyclerView))
                .check(matches(isDisplayed()))

            true
        } catch (_: NoMatchingViewException) {
            false
        } catch (_: AssertionError) {
            false
        }
    }

    // -------------------------
    // OPEN CHAT
    // -------------------------


    fun openChat(chatName: String): ConversationPage {

        // Navigate to the Chats screen.
        WaitUtils.waitForView(
            withId(R.id.homeNavigationChats),
            10000
        )

        onView(withId(R.id.homeNavigationChats))
            .perform(click())

        // Wait until the chat list is displayed.
        WaitUtils.waitForView(
            withId(R.id.chatsRecyclerView),
            10000
        )

        // Search for the requested chat.
        chatsPage.searchForChat(chatName)

        // Match the conversation icon belonging to this chat.
        val conversationButton = allOf(
            withId(DesignSystemR.id.sendMessageButton),
            hasSibling(
                allOf(
                    withId(DesignSystemR.id.chatTitleTextView),
                    withText(chatName)
                )
            ),
            isDescendantOfA(
                withId(R.id.chatsRecyclerView)
            ),
            isDisplayed()
        )

        // Wait until the matching button is visible.
        WaitUtils.waitForView(
            conversationButton,
            10000
        )

        // Click the conversation icon.
        onView(conversationButton)
            .perform(click())

        // Verify the correct conversation opened.
        return verifyChatTitle(chatName)
    }


    // -------------------------
    // VERIFY CHAT TITLE
    // -------------------------

    fun verifyChatTitle(chatName: String): ConversationPage {

        val titleMatcher = allOf(
            withId(DesignSystemR.id.chatNameTextView),
            withText(chatName),
            isDescendantOfA(
                withId(R.id.chatHeaderView)
            ),
            isDisplayed()
        )

        WaitUtils.waitForView(
            titleMatcher,
            10000
        )

        onView(titleMatcher)
            .check(matches(isDisplayed()))

        return this
    }

    // -------------------------
    // SEND MESSAGE
    // -------------------------

    fun sendMessage(text: String): ConversationPage {

        WaitUtils.waitForView(
            allOf(messageInput, isDisplayed()),
            10000
        )

        onView(messageInput)
            .perform(
                replaceText(text),
                closeSoftKeyboard()
            )

        WaitUtils.waitForView(
            withId(R.id.sendMessageButton),
            10000
        )

        onView(withId(R.id.sendMessageButton))
            .perform(click())

        return this
    }

    // -------------------------
    // VERIFY VISIBLE MESSAGE
    // -------------------------

    fun verifyMessageVisible(text: String): ConversationPage {

        val matcher = allOf(
            messageMatcher(text),
            isDisplayed()
        )

        WaitUtils.waitForView(
            matcher,
            10000
        )

        onView(matcher)
            .check(matches(isDisplayed()))

        return this
    }

    // -------------------------
    // SCROLL TO MESSAGE
    // -------------------------

    fun scrollToMessage(text: String): ConversationPage {

        onView(withId(R.id.messagesRecyclerView))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(
                        allOf(
                            withId(DesignSystemR.id.messageTextView),
                            withText(text)
                        )
                    )
                )
            )

        return verifyMessageVisible(text)
    }

    // -------------------------
    // VERIFY MESSAGE SENDER
    // -------------------------

    fun verifyMessageSender(
        text: String,
        expectedUsername: String
    ): ConversationPage {

        var found = false

        onView(withId(R.id.messagesRecyclerView))
            .check { view, error ->

                if (error != null) {
                    throw error
                }

                val recyclerView = view as RecyclerView

                for (i in 0 until recyclerView.childCount) {

                    val row = recyclerView.getChildAt(i)

                    val messageView = row.findViewById<TextView>(
                        DesignSystemR.id.messageTextView
                    )

                    if (messageView?.text?.toString() == text) {

                        found = true

                        val senderView = row.findViewById<TextView>(
                            DesignSystemR.id.userNameTextView
                        )

                        val actualSender =
                            senderView?.text?.toString().orEmpty()

                        check(
                            actualSender.contains(
                                expectedUsername,
                                ignoreCase = true
                            )
                        ) {
                            "Wrong message sender. " +
                                    "Expected: $expectedUsername, " +
                                    "Actual: $actualSender"
                        }

                        break
                    }
                }
            }

        check(found) {
            "Message '$text' was not found in visible rows"
        }

        return this
    }

    // -------------------------
    // GET VISIBLE MESSAGE AREA
    // -------------------------

    private fun messageArea(): Pair<Int, Int> {

        val headerRect = Rect()
        val inputRect = Rect()

        onView(withId(R.id.chatHeaderView))
            .check { view, error ->

                if (error != null) throw error

                check(view != null &&
                        view.getGlobalVisibleRect(headerRect)) {
                    "Chat header is not visible"
                }
            }

        onView(withId(R.id.messageInput))
            .check { view, error ->

                if (error != null) throw error

                check(view != null &&
                        view.getGlobalVisibleRect(inputRect)) {
                    "Message input is not visible"
                }
            }

        val top = headerRect.bottom
        val bottom = inputRect.top

        check(bottom - top > 80) {
            "Insufficient space for swiping messages"
        }

        return top to bottom
    }

    // -------------------------
    // CHECK MESSAGE VISIBILITY
    // -------------------------

    fun isMessageVisible(text: String): Boolean {

        val (top, bottom) = messageArea()
        var visible = false

        onView(withId(R.id.messagesRecyclerView))
            .check { view, error ->

                if (error != null) throw error

                val recyclerView = view as RecyclerView

                for (i in 0 until recyclerView.childCount) {

                    val row = recyclerView.getChildAt(i)

                    val messageView = row.findViewById<TextView>(
                        DesignSystemR.id.messageTextView
                    )

                    if (messageView?.text?.toString() == text) {

                        val rect = Rect()

                        visible =
                            messageView.isShown &&
                                    messageView.getGlobalVisibleRect(rect) &&
                                    rect.bottom > top &&
                                    rect.top < bottom

                        if (visible) break
                    }
                }
            }

        return visible
    }

    // -------------------------
    // SWIPE TO OLDER MESSAGE
    // -------------------------

    fun swipeToOlderMessage(
        text: String,
        sender: String
    ): ConversationPage {

        val (top, bottom) = messageArea()

        val height = bottom - top

        // Coordinates adapt to screen size.
        val startY = top + (height * 0.25).toInt()
        val endY = top + (height * 0.75).toInt()

        // The chat RecyclerView uses reverseLayout.
        // Swiping down reveals older messages.
        repeat(35) {

            // Reuse the project's existing helper.
            swiper(
                startY,
                endY,
                250
            )

            if (isMessageVisible(text)) {

                verifyMessageVisible(text)

                verifyMessageSender(
                    text,
                    sender
                )

                return this
            }
        }

        throw AssertionError(
            "Message '$text' not found after 35 swipes"
        )
    }

    // -------------------------
    // CLOSE CONVERSATION
    // -------------------------

    fun closeChat(): ConversationPage {

        WaitUtils.waitForView(
            withId(R.id.closeButton),
            10000
        )

        onView(withId(R.id.closeButton))
            .perform(click())

        WaitUtils.waitForView(
            withId(R.id.chatsRecyclerView),
            10000
        )

        return this
    }
}
