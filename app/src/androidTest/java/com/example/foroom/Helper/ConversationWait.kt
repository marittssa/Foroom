
package com.example.foroom.helper

import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.alternator.foroom.R
import com.example.design_system.R as DesignSystemR
import org.hamcrest.Matchers.allOf
import org.junit.Assert.fail

object ConversationWait {

    fun untilMessageVisible(
        message: String,
        timeoutMs: Long = 10000
    ) {
        val endTime = SystemClock.uptimeMillis() + timeoutMs

        val matcher = allOf(
            withId(DesignSystemR.id.messageTextView),
            withText(message),
            isDescendantOfA(withId(R.id.messagesRecyclerView)),
            isDisplayed()
        )

        while (SystemClock.uptimeMillis() < endTime) {
            try {
                onView(matcher).check(matches(isDisplayed()))
                return
            } catch (_: AssertionError) {
                // The expected message isn't ready yet.
            } catch (_: androidx.test.espresso.NoMatchingViewException) {
                // The expected message isn't in the visible hierarchy.
            }

            InstrumentationRegistry.getInstrumentation()
                .waitForIdleSync()

            SystemClock.sleep(150)
        }

        fail("Message '$message' did not appear within ${timeoutMs}ms")
    }
}
