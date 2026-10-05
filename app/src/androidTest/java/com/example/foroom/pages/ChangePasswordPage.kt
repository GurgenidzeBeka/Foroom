package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.design_system.R as DesignR
import com.example.foroom.Helper.waitUntilVisible
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class ChangePasswordPage {
    val passwordInput: Matcher<View> = withId(R.id.passwordInput)
    val repeatPasswordInput: Matcher<View> = withId(R.id.repeatPasswordInput)
    val actionButton: Matcher<View> = withId(DesignR.id.actionButton)

    private val passwordEditText: Matcher<View> = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(R.id.passwordInput))
    )

    private val repeatPasswordEditText: Matcher<View> = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(R.id.repeatPasswordInput))
    )

    fun enterPassword(password: String) {
        onView(passwordEditText).waitUntilVisible(5)
            .perform(replaceText(password), closeSoftKeyboard())
    }

    fun enterRepeatedPassword(password: String) {
        onView(repeatPasswordEditText).waitUntilVisible(5)
            .perform(replaceText(password), closeSoftKeyboard())
    }

    fun tapConfirm() {
        onView(actionButton).waitUntilVisible(5).perform(click())
    }
}