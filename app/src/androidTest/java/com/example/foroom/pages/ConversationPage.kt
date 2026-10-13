package com.example.foroom.pages

import android.os.SystemClock
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.design_system.R as DesignR
import com.example.foroom.Helper.swiper
import com.example.foroom.Helper.waitUntilVisible
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class ConversationPage {
    val messagesRecyclerView: Matcher<View> = withId(R.id.messagesRecyclerView)
    val closeButton: Matcher<View> = withId(R.id.closeButton)
    val sendMessageButton: Matcher<View> = withId(R.id.sendMessageButton)

    private val messageEditText: Matcher<View> = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(R.id.messageInput))
    )

    fun chatTitle(chatName: String): Matcher<View> = allOf(
        withId(DesignR.id.chatNameTextView),
        withText(chatName),
        isDescendantOfA(withId(R.id.chatHeaderView))
    )

    fun messageBubble(message: String, sender: String): Matcher<View> = allOf(
        withId(R.id.messageView),
        isDescendantOfA(withId(R.id.messagesRecyclerView)),
        hasDescendant(allOf(withId(DesignR.id.messageTextView), withText(message))),
        hasDescendant(allOf(withId(DesignR.id.userNameTextView), withText(sender)))
    )

    fun enterMessage(message: String) {
        onView(messageEditText).waitUntilVisible(5)
            .perform(replaceText(message), closeSoftKeyboard())
    }

    fun tapSend() {
        waitUntilSendEnabled()
        onView(sendMessageButton).perform(click())
    }

    fun closeConversation() {
        onView(closeButton).waitUntilVisible(5).perform(click())
    }

    fun swipeTowardOlderMessages() {
        var startY = 0
        var endY = 0

        onView(messagesRecyclerView).waitUntilVisible(5).perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> = isDisplayed()

            override fun getDescription(): String =
                "calculate device-independent swipe coordinates from messages RecyclerView"

            override fun perform(uiController: UiController, view: View) {
                val location = IntArray(2)
                view.getLocationOnScreen(location)
                val top = location[1]
                startY = top + (view.height * 0.30f).toInt()
                endY = top + (view.height * 0.70f).toInt()
            }
        })

        swiper(startY, endY, 300)
    }

    private fun waitUntilSendEnabled(timeoutSec: Long = 5) {
        val endTime = System.currentTimeMillis() + timeoutSec * 1000

        do {
            try {
                onView(sendMessageButton).check(matches(isEnabled()))
                return
            } catch (_: RuntimeException) {
            } catch (_: AssertionError) {
            }

            SystemClock.sleep(50)
        } while (System.currentTimeMillis() < endTime)

        throw AssertionError("Send message button did not become enabled within $timeoutSec seconds")
    }
}