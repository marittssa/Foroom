package com.example.foroom.steps

import com.example.foroom.pages.ChangeLanguagePage
import com.example.foroom.pages.ChangePasswordPage
import com.example.foroom.pages.ProfilePage

class ProfileSteps {

    private val profilePage = ProfilePage()

    private val changePasswordPage =
        ChangePasswordPage()

    private val changeLanguagePage =
        ChangeLanguagePage()

    fun openProfile(): ProfileSteps {

        profilePage.openProfile()

        return this
    }

    fun changePassword(
        newPassword: String
    ): ProfileSteps {

        profilePage.clickChangePassword()

        changePasswordPage
            .enterPassword(newPassword)
            .repeatPassword(newPassword)
            .clickConfirm()

        return this
    }

    fun openChangeLanguage(): ProfileSteps {

        profilePage.clickChangeLanguage()

        return this
    }

    fun selectGeorgian(): ProfileSteps {

        changeLanguagePage.selectGeorgian()

        return this
    }

    fun selectEnglish(): ProfileSteps {

        changeLanguagePage.selectEnglish()

        return this
    }

    fun verifyGeorgianLanguage(): ProfileSteps {

        profilePage.verifyGeorgianLanguage()

        return this
    }

    fun verifyEnglishLanguage(): ProfileSteps {

        profilePage.verifyEnglishLanguage()

        return this
    }

    fun signOut(): ProfileSteps {

        profilePage.clickSignOut()

        return this
    }
}