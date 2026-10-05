package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import com.example.foroom.steps.ChatSteps
import com.example.foroom.steps.LoginSteps
import com.example.foroom.steps.ProfileSteps
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.model.Statement
import org.koin.core.context.GlobalContext

@RunWith(AndroidJUnit4::class)
class ProfileAndChatTests {
    private val loginSteps = LoginSteps()
    private val profileSteps = ProfileSteps()
    private val chatSteps = ChatSteps()
    private val activityRule = ActivityScenarioRule(ForoomActivity::class.java)

    private val resetSessionRule = TestRule { base, _ ->
        object : Statement() {
            override fun evaluate() {
                val userDataStore = GlobalContext.get().get<ForoomUserDataStore>()
                runBlocking { userDataStore.clearUserData() }

                try {
                    base.evaluate()
                } finally {
                    runBlocking { userDataStore.clearUserData() }
                }
            }
        }
    }

    @get:Rule
    val rules: TestRule = RuleChain
        .outerRule(resetSessionRule)
        .around(activityRule)

    @Test
    fun changePasswordAndVerifyWithNewLogin() {
        loginSteps.verifyLoginScreenDisplayed()
        loginSteps.logIn(TEST_USERNAME, CURRENT_PASSWORD)
        profileSteps.verifyHomeScreenDisplayed()

        profileSteps.openProfile()
        profileSteps.openChangePassword()
        profileSteps.changePassword(NEW_PASSWORD)

        loginSteps.verifyLoginScreenDisplayed()
        loginSteps.logIn(TEST_USERNAME, NEW_PASSWORD)
        profileSteps.verifyHomeScreenDisplayed()

        profileSteps.openProfile()
        profileSteps.openChangePassword()
        profileSteps.changePassword(CURRENT_PASSWORD)
        loginSteps.verifyLoginScreenDisplayed()
    }

    @Test
    fun changeLanguageFromGeorgianToEnglishAndBack() {
        loginSteps.verifyLoginScreenDisplayed()
        loginSteps.logIn(TEST_USERNAME, CURRENT_PASSWORD)
        profileSteps.verifyHomeScreenDisplayed()
        profileSteps.openProfile()

        profileSteps.openChangeLanguage()
        profileSteps.selectGeorgian()
        profileSteps.verifyGeorgianProfileLabel()

        profileSteps.openChangeLanguage()
        profileSteps.selectEnglish()
        profileSteps.verifyEnglishProfileLabel()

        profileSteps.openChangeLanguage()
        profileSteps.selectGeorgian()
        profileSteps.verifyGeorgianProfileLabel()
    }

    @Test
    fun createChatAndFindItInChatList() {
        val chatName = "$FULL_NAME_FOR_CHAT ${System.currentTimeMillis()}"

        loginSteps.verifyLoginScreenDisplayed()
        loginSteps.logIn(TEST_USERNAME, CURRENT_PASSWORD)
        profileSteps.verifyHomeScreenDisplayed()

        chatSteps.openCreateChat()
        chatSteps.createChat(chatName)
        chatSteps.verifyCreatedChatScreen(chatName)
        chatSteps.closeCreatedChat()
        chatSteps.searchForChat(chatName)
        chatSteps.verifyChatFound(chatName)
    }

    companion object {
        private const val TEST_USERNAME = "creatureOfTheNight"
        private const val CURRENT_PASSWORD = "okeypassword"
        private const val NEW_PASSWORD = "okeypasswordDOS"
        private const val FULL_NAME_FOR_CHAT = "Beka Gurgenidze"
    }
}