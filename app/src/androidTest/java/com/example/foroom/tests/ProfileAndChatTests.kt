package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.steps.ChatSteps
import com.example.foroom.steps.LoginSteps
import com.example.foroom.steps.ProfileSteps
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileAndChatTests {

    @get:Rule
    val activityRule = ActivityScenarioRule(ForoomActivity::class.java)

    private val loginSteps = LoginSteps()
    private val profileSteps = ProfileSteps()
    private val chatSteps = ChatSteps()

    companion object {
        private const val TEST_USERNAME = "mytestaccount"
        private const val CURRENT_PASSWORD = "MyCurrentPassword123!"
        private const val NEW_PASSWORD = "MyNewPassword456!"
        private const val FULL_NAME = "Maritsa Machaidze"
    }

    @Before
    fun ensureLoggedOutBeforeTest() {
        if (!loginSteps.isLoginScreenDisplayed()) {
            logout()
        }
    }

    @Test
    fun changePasswordAndVerifyIt() {
        login()

        profileSteps
            .openProfile()
            .changePassword(NEW_PASSWORD)

        loginSteps
            .verifyLoginPage()
            .login(TEST_USERNAME, NEW_PASSWORD)
            .verifyHomeScreen()

        profileSteps
            .openProfile()
            .changePassword(CURRENT_PASSWORD)

        loginSteps
            .verifyLoginPage()
            .login(TEST_USERNAME, CURRENT_PASSWORD)
            .verifyHomeScreen()

        logout()
    }

    @Test
    fun changeLanguageFromGeorgianToEnglishAndBack() {
        login()

        profileSteps
            .openProfile()
            .openChangeLanguage()
            .selectGeorgian()

        profileSteps
            .openProfile()
            .verifyGeorgianLanguage()
            .openChangeLanguage()
            .selectEnglish()

        profileSteps
            .openProfile()
            .verifyEnglishLanguage()
            .openChangeLanguage()
            .selectGeorgian()

        profileSteps
            .openProfile()
            .verifyGeorgianLanguage()

        logout()
    }

    @Test
    fun createChatAndFindItInChatList() {
        login()

        val uniqueSuffix = System.currentTimeMillis()
            .toString()
            .takeLast(6)

        val chatName = "$FULL_NAME $uniqueSuffix"

        chatSteps
            .createChat(chatName)
            .verifyCreatedChatOpened(chatName)
            .closeChat()
            .searchForChat(chatName)
            .verifyChatInList(chatName)

        logout()
    }

    private fun login() {
        loginSteps
            .login(TEST_USERNAME, CURRENT_PASSWORD)
            .verifyHomeScreen()
    }

    private fun logout() {
        profileSteps
            .openProfile()
            .signOut()

        loginSteps.verifyLoginPage()
    }
}