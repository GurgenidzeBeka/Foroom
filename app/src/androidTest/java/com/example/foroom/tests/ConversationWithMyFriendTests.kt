package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import com.example.foroom.steps.ConversationSteps
import com.example.foroom.steps.SessionSteps
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.model.Statement
import org.koin.core.context.GlobalContext

@RunWith(AndroidJUnit4::class)
class ConversationWithMyFriendTests {
    private val sessionSteps = SessionSteps()
    private val conversationSteps = ConversationSteps()
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
    fun sendMessageInJohnWeekAndVerifyAfterReopening() {
        val message = "let's go for a drink? ${System.currentTimeMillis()}"

        sessionSteps.logIn(USER_A_USERNAME, USER_A_PASSWORD)
        conversationSteps.openChat(JOHN_WEEK_CHAT)
        conversationSteps.sendAndVerifyMessage(message, USER_A_USERNAME)

        conversationSteps.closeChat()
        conversationSteps.openChat(JOHN_WEEK_CHAT)
        conversationSteps.verifyMessage(message, USER_A_USERNAME)
    }

    @Test
    fun sendAutomationAcademyQuestionInMyChat() {
        val question =
            "Which Automation Academy module do you like most? ${System.currentTimeMillis()}"

        sessionSteps.logIn(USER_A_USERNAME, USER_A_PASSWORD)
        conversationSteps.openChat(MY_CHAT)
        conversationSteps.sendAndVerifyMessage(question, USER_A_USERNAME)
    }

    @Test
    fun continueConversationUsingAnotherAccount() {
        val suffix = System.currentTimeMillis()
        val greeting = "Dark Greetings $suffix"
        val fillerPrefix = "Bat! $suffix"
        val reply = "blade blade blade $suffix"

        sessionSteps.logIn(USER_A_USERNAME, USER_A_PASSWORD)
        conversationSteps.openChat(SHARED_CHAT)
        conversationSteps.sendAndVerifyMessage(greeting, USER_A_USERNAME)

        conversationSteps.sendAdditionalMessages(
            fillerPrefix,
            USER_A_USERNAME,
            ADDITIONAL_MESSAGE_COUNT
        )

        conversationSteps.closeChat()
        sessionSteps.signOut()

        sessionSteps.logIn(USER_B_USERNAME, USER_B_PASSWORD)
        conversationSteps.openChat(SHARED_CHAT)
        conversationSteps.findOlderMessage(greeting, USER_A_USERNAME)
        conversationSteps.sendAndVerifyMessage(reply, USER_B_USERNAME)

        conversationSteps.closeChat()
        sessionSteps.signOut()

        sessionSteps.logIn(USER_A_USERNAME, USER_A_PASSWORD)
        conversationSteps.openChat(SHARED_CHAT)
        conversationSteps.verifyMessage(reply, USER_B_USERNAME)
    }

    companion object {
        private const val USER_A_USERNAME = "creatureOfTheNight"
        private const val USER_A_PASSWORD = "okeypassword"

        private const val USER_B_USERNAME = "dayWalker"
        private const val USER_B_PASSWORD = "okeypassword"

        private const val JOHN_WEEK_CHAT = "jonhWeek"
        private const val MY_CHAT = "Beka Gurgenidze 1791231503826"
        private const val SHARED_CHAT = "something"

        private const val ADDITIONAL_MESSAGE_COUNT = 25
    }
}