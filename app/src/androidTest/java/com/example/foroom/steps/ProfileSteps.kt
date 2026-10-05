package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.pages.ChangeLanguagePage
import com.example.foroom.pages.ChangePasswordPage
import com.example.foroom.pages.ProfilePage

class ProfileSteps(
    private val profilePage: ProfilePage = ProfilePage(),
    private val changePasswordPage: ChangePasswordPage = ChangePasswordPage(),
    private val changeLanguagePage: ChangeLanguagePage = ChangeLanguagePage()
) {
    fun verifyHomeScreenDisplayed() {
        onView(profilePage.navBar).waitUntilVisible(10).check(matches(isDisplayed()))
    }

    fun openProfile() {
        profilePage.openProfile()
        onView(profilePage.changePasswordItem)
            .waitUntilVisible(5)
            .check(matches(isDisplayed()))
    }

    fun openChangePassword() {
        profilePage.tapChangePassword()
        onView(changePasswordPage.passwordInput)
            .waitUntilVisible(5)
            .check(matches(isDisplayed()))
    }

    fun changePassword(newPassword: String) {
        changePasswordPage.enterPassword(newPassword)
        changePasswordPage.enterRepeatedPassword(newPassword)
        changePasswordPage.tapConfirm()
    }

    fun openChangeLanguage() {
        profilePage.tapChangeLanguage()
        onView(changeLanguagePage.georgianButton)
            .waitUntilVisible(5)
            .check(matches(isDisplayed()))
    }

    fun selectGeorgian() {
        changeLanguagePage.selectGeorgian()
    }

    fun selectEnglish() {
        changeLanguagePage.selectEnglish()
    }

    fun verifyGeorgianProfileLabel() {
        onView(profilePage.changeLanguageLabel("ენის შეცვლა"))
            .waitUntilVisible(10)
            .check(matches(isDisplayed()))
    }

    fun verifyEnglishProfileLabel() {
        onView(profilePage.changeLanguageLabel("Change Language"))
            .waitUntilVisible(10)
            .check(matches(isDisplayed()))
    }
}