package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.pages.ChatsPage
import com.example.foroom.pages.CreateChatPage

class ChatSteps(
    private val createChatPage: CreateChatPage = CreateChatPage(),
    private val chatsPage: ChatsPage = ChatsPage()
) {
    fun openCreateChat() {
        createChatPage.openCreateChat()
        onView(createChatPage.chatNameInput)
            .waitUntilVisible(5)
            .check(matches(isDisplayed()))
    }

    fun createChat(chatName: String) {
        createChatPage.enterChatName(chatName)
        createChatPage.selectChatImage()
        createChatPage.tapCreateChat()
    }

    fun verifyCreatedChatScreen(chatName: String) {
        onView(createChatPage.messagesRecyclerView)
            .waitUntilVisible(10)
            .check(matches(isDisplayed()))

        onView(createChatPage.createdChatTitle(chatName))
            .check(matches(isDisplayed()))
    }

    fun closeCreatedChat() {
        createChatPage.closeCreatedChat()
        onView(chatsPage.searchChatInput)
            .waitUntilVisible(10)
            .check(matches(isDisplayed()))
    }

    fun searchForChat(chatName: String) {
        chatsPage.searchForChat(chatName)
    }

    fun verifyChatFound(chatName: String) {
        onView(chatsPage.chatTitle(chatName))
            .waitUntilVisible(10)
            .check(matches(isDisplayed()))
    }
}