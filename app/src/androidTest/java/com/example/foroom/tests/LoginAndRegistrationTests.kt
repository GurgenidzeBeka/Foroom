package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import com.example.foroom.steps.LoginSteps
import com.example.foroom.steps.RegistrationSteps
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.model.Statement
import org.koin.core.context.GlobalContext

@RunWith(AndroidJUnit4::class)
class LoginAndRegistrationTests {

    private val loginSteps = LoginSteps()
    private val registrationSteps = RegistrationSteps()

    private val activityRule = ActivityScenarioRule(ForoomActivity::class.java)

    private val resetUserRule = TestRule { base, _ ->
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
        .outerRule(resetUserRule)
        .around(activityRule)

    @Test
    fun validUserNameAndInvalidPassword_displaysPasswordError() {
        loginSteps.verifyLoginScreenDisplayed()

        loginSteps.logIn(
            userName = "student",
            password = "WrongPassword123!"
        )

        loginSteps.verifyPasswordErrorDisplayed()
    }

    @Test
    fun invalidUserNameAndInvalidPassword_displayBothErrors() {
        loginSteps.verifyLoginScreenDisplayed()

        loginSteps.logIn(
            userName = "missing_${System.currentTimeMillis()}",
            password = "WrongPassword123!"
        )

        loginSteps.verifyUserNameErrorDisplayed()
        loginSteps.verifyPasswordErrorDisplayed()
    }

    @Test
    fun successfulRegistration_displaysHomeScreen() {
        loginSteps.verifyLoginScreenDisplayed()
        loginSteps.openRegistration()
        registrationSteps.verifyRegistrationScreenDisplayed()

        registrationSteps.register(
            userName = "espresso_${System.currentTimeMillis()}",
            password = "Password123!"
        )

        registrationSteps.verifyHomeScreenDisplayed()
    }
}