package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.design_system.R as DesignR
import com.example.foroom.Helper.waitUntilVisible
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class ChatsPage {
    val searchChatInput: Matcher<View> = withId(R.id.searchChatInput)
    val chatsRecyclerView: Matcher<View> = withId(R.id.chatsRecyclerView)

    private val searchEditText: Matcher<View> = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(R.id.searchChatInput))
    )

    fun chatTitle(chatName: String): Matcher<View> = allOf(
        withId(DesignR.id.chatTitleTextView),
        withText(chatName),
        isDescendantOfA(withId(R.id.chatsRecyclerView))
    )

    fun chatMessageButton(chatName: String): Matcher<View> = allOf(
        withId(DesignR.id.sendMessageButton),
        isDescendantOfA(
            hasDescendant(
                allOf(
                    withId(DesignR.id.chatTitleTextView),
                    withText(chatName)
                )
            )
        )
    )

    fun searchForChat(chatName: String) {
        onView(searchEditText).waitUntilVisible(5)
            .perform(replaceText(chatName), closeSoftKeyboard())
    }
}