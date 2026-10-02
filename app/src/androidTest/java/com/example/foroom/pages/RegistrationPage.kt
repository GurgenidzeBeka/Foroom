package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.util.HumanReadables
import com.alternator.foroom.R
import com.example.design_system.R as DesignR
import com.example.design_system.components.image_chooser.ImageChooserItemView
import com.example.design_system.components.image_chooser.ImageChooserListView
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.Helper.withIndex
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import java.util.concurrent.TimeoutException

class RegistrationPage {

    val repeatPasswordInput: Matcher<View> = withId(R.id.repeatPasswordInput)
    val avatarList: Matcher<View> = withId(R.id.listView)
    val signUpButton: Matcher<View> = withId(R.id.signUpButton)
    val homeNavBar: Matcher<View> = withId(R.id.navBar)

    private val userNameEditText: Matcher<View> = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(R.id.userNameInput))
    )

    private val passwordEditText: Matcher<View> = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(R.id.passwordInput))
    )

    private val repeatPasswordEditText: Matcher<View> = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(R.id.repeatPasswordInput))
    )

    private val avatarItem: Matcher<View> = allOf(
        isAssignableFrom(ImageChooserItemView::class.java),
        isDescendantOfA(withId(R.id.listView))
    )

    fun enterUserName(userName: String) {
        onView(userNameEditText)
            .waitUntilVisible(5)
            .perform(replaceText(userName), closeSoftKeyboard())
    }

    fun enterPassword(password: String) {
        onView(passwordEditText)
            .waitUntilVisible(5)
            .perform(replaceText(password), closeSoftKeyboard())
    }

    fun enterRepeatedPassword(password: String) {
        onView(repeatPasswordEditText)
            .waitUntilVisible(5)
            .perform(replaceText(password), closeSoftKeyboard())
    }

    fun selectAvatar(index: Int = 1) {
        onView(avatarList)
            .waitUntilVisible(5)
            .perform(waitUntilAvatarListReady())

        onView(withIndex(avatarItem, index))
            .perform(click())
    }

    fun tapSignUp() {
        onView(signUpButton)
            .waitUntilVisible(5)
            .perform(click())
    }

    private fun waitUntilAvatarListReady(timeoutMillis: Long = 5_000): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> =
                isAssignableFrom(ImageChooserListView::class.java)

            override fun getDescription(): String =
                "wait until avatar selection is enabled"

            override fun perform(uiController: UiController, view: View) {
                val listView = view as ImageChooserListView
                val endTime = System.currentTimeMillis() + timeoutMillis

                while (System.currentTimeMillis() < endTime) {
                    if (listView.isChoosingEnabled && listView.childCount > 0) return
                    uiController.loopMainThreadForAtLeast(50)
                }

                throw PerformException.Builder()
                    .withActionDescription(description)
                    .withViewDescription(HumanReadables.describe(view))
                    .withCause(TimeoutException())
                    .build()
            }
        }
    }
}