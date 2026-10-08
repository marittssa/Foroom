
package com.example.foroom.helper

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.alternator.foroom.R
import com.example.design_system.R as DesignSystemR
import org.hamcrest.Matchers.allOf

class ConversationAssertions {

    private fun messageBubble(message: String) = allOf(
        withId(R.id.messageView),
        hasDescendant(
            allOf(
                withId(DesignSystemR.id.messageTextView),
                withText(message)
            )
        )
    )

    fun verifyVisibleMessage(message: String) {
        onView(
            allOf(
                withId(DesignSystemR.id.messageTextView),
                withText(message),
                isDescendantOfA(withId(R.id.messagesRecyclerView)),
                isDisplayed()
            )
        ).check(matches(isDisplayed()))
    }

    fun verifyVisibleMessageFromSender(
        message: String,
        sender: String
    ) {
        onView(
            allOf(
                messageBubble(message),
                hasDescendant(
                    allOf(
                        withId(DesignSystemR.id.userNameTextView),
                        withText(sender)
                    )
                ),
                isDisplayed()
            )
        ).check(matches(isDisplayed()))
    }
}
