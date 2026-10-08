package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.pages.LoginPage

class LoginSteps(private val loginPage: LoginPage = LoginPage()) {

    fun verifyLoginScreenDisplayed() {
        onView(loginPage.userNameInput)
            .waitUntilVisible(5)
            .check(matches(isDisplayed()))

        onView(loginPage.logInButton)
            .check(matches(isDisplayed()))
    }

    fun logIn(userName: String, password: String) {
        loginPage.enterUserName(userName)
        loginPage.enterPassword(password)
        loginPage.tapLogIn()
    }

    fun openRegistration() {
        loginPage.tapSignUp()
    }

    fun verifyUserNameErrorDisplayed() {
        onView(loginPage.userNameError)
            .waitUntilVisible(5)
            .check(matches(isDisplayed()))
    }

    fun verifyPasswordErrorDisplayed() {
        onView(loginPage.passwordError)
            .waitUntilVisible(5)
            .check(matches(isDisplayed()))
    }
}