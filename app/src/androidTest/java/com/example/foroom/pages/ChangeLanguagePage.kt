package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.foroom.Helper.waitUntilVisible
import org.hamcrest.Matcher

class ChangeLanguagePage {
    val georgianButton: Matcher<View> = withId(R.id.languageButtonGeo)
    val englishButton: Matcher<View> = withId(R.id.languageButtonEng)

    fun selectGeorgian() {
        onView(georgianButton).waitUntilVisible(5).perform(click())
    }

    fun selectEnglish() {
        onView(englishButton).waitUntilVisible(5).perform(click())
    }
}