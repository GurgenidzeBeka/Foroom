package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.pages.ProfilePage

class SessionSteps(
    private val loginSteps: LoginSteps = LoginSteps(),
    private val profilePage: ProfilePage = ProfilePage()
) {
    fun logIn(userName: String, password: String) {
        loginSteps.verifyLoginScreenDisplayed()
        loginSteps.logIn(userName, password)
        onView(profilePage.navBar).waitUntilVisible(10).check(matches(isDisplayed()))
    }

    fun signOut() {
        profilePage.openProfile()
        onView(profilePage.signOutItem).waitUntilVisible(5).perform(click())
        loginSteps.verifyLoginScreenDisplayed()
    }
}