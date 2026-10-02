package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.pages.RegistrationPage

class RegistrationSteps(private val registrationPage: RegistrationPage = RegistrationPage()) {

    fun verifyRegistrationScreenDisplayed() {
        onView(registrationPage.repeatPasswordInput)
            .waitUntilVisible(5)
            .check(matches(isDisplayed()))

        onView(registrationPage.avatarList)
            .check(matches(isDisplayed()))
    }

    fun register(userName: String, password: String) {
        registrationPage.enterUserName(userName)
        registrationPage.enterPassword(password)
        registrationPage.enterRepeatedPassword(password)
        registrationPage.selectAvatar()
        registrationPage.tapSignUp()
    }

    fun verifyHomeScreenDisplayed() {
        onView(registrationPage.homeNavBar)
            .waitUntilVisible(10)
            .check(matches(isDisplayed()))
    }
}