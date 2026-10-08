package com.example.foroom.helper

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import org.hamcrest.Matcher

object WaitUtils {

    fun waitForView(
        matcher: Matcher<View>,
        timeoutMs: Long = 5000
    ) {

        onView(isRoot()).perform(
            object : ViewAction {

                override fun getConstraints(): Matcher<View> {
                    return isRoot()
                }

                override fun getDescription(): String {
                    return "Wait for matching view"
                }

                override fun perform(
                    uiController: UiController,
                    view: View
                ) {

                    val endTime =
                        System.currentTimeMillis() + timeoutMs

                    while (
                        System.currentTimeMillis() < endTime
                    ) {

                        if (findView(view, matcher)) {
                            return
                        }

                        uiController.loopMainThreadForAtLeast(50)
                    }

                    throw AssertionError(
                        "View was not found within $timeoutMs ms"
                    )
                }
            }
        )
    }

    private fun findView(
        view: View,
        matcher: Matcher<View>
    ): Boolean {

        if (matcher.matches(view)) {
            return true
        }

        if (view is ViewGroup) {

            for (i in 0 until view.childCount) {

                if (
                    findView(
                        view.getChildAt(i),
                        matcher
                    )
                ) {
                    return true
                }
            }
        }

        return false
    }
}