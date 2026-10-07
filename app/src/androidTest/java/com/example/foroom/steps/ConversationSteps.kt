package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.pages.ChatsPage
import com.example.foroom.pages.ConversationPage

class ConversationSteps(
    private val chatsPage: ChatsPage = ChatsPage(),
    private val conversationPage: ConversationPage = ConversationPage()
) {
    fun openChat(chatName: String) {
        chatsPage.searchForChat(chatName)

        onView(chatsPage.chatMessageButton(chatName))
            .waitUntilVisible(10)
            .perform(click())

        verifyConversationOpened(chatName)
    }

    fun verifyConversationOpened(chatName: String) {
        onView(conversationPage.chatTitle(chatName))
            .waitUntilVisible(10)
            .check(matches(isDisplayed()))
    }

    fun sendAndVerifyMessage(message: String, sender: String) {
        conversationPage.enterMessage(message)
        conversationPage.tapSend()
        verifyMessage(message, sender)
    }

    fun verifyMessage(message: String, sender: String) {
        onView(conversationPage.messageBubble(message, sender))
            .waitUntilVisible(10)
            .check(matches(isDisplayed()))
    }

    fun closeChat() {
        conversationPage.closeConversation()
        onView(chatsPage.searchChatInput)
            .waitUntilVisible(10)
            .check(matches(isDisplayed()))
    }

    fun sendAdditionalMessages(prefix: String, sender: String, count: Int) {
        repeat(count) { index ->
            sendAndVerifyMessage("$prefix ${index + 1}", sender)
        }
    }

    fun findOlderMessage(message: String, sender: String, maxSwipes: Int = 10) {
        repeat(maxSwipes + 1) { attempt ->
            if (isMessageDisplayed(message, sender)) return

            if (attempt < maxSwipes) {
                conversationPage.swipeTowardOlderMessages()
            }
        }

        verifyMessage(message, sender)
    }

    private fun isMessageDisplayed(message: String, sender: String): Boolean {
        return try {
            onView(conversationPage.messageBubble(message, sender))
                .check(matches(isDisplayed()))
            true
        } catch (_: RuntimeException) {
            false
        } catch (_: AssertionError) {
            false
        }
    }
}