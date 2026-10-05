package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.design_system.R as DesignR
import com.example.foroom.Helper.waitUntilVisible
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class ProfilePage {
    val navBar: Matcher<View> = withId(R.id.navBar)
    val profileNavigation: Matcher<View> = withId(R.id.homeNavigationProfile)
    val changePasswordItem: Matcher<View> = withId(R.id.changePasswordItem)
    val changeLanguageItem: Matcher<View> = withId(R.id.changeLanguageItem)
    val signOutItem: Matcher<View> = withId(R.id.signOutItem)

    fun changeLanguageLabel(text: String): Matcher<View> = allOf(
        withId(DesignR.id.listItemTextView),
        withText(text),
        isDescendantOfA(withId(R.id.changeLanguageItem))
    )

    fun openProfile() {
        onView(profileNavigation).waitUntilVisible(10).perform(click())
    }

    fun tapChangePassword() {
        onView(changePasswordItem).waitUntilVisible(5).perform(click())
    }

    fun tapChangeLanguage() {
        onView(changeLanguageItem).waitUntilVisible(5).perform(click())
    }
}