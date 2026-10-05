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
import androidx.test.espresso.matcher.ViewMatchers.withText
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

class CreateChatPage {
    val createChatNavigation: Matcher<View> = withId(R.id.homeNavigationCreateChat)
    val chatNameInput: Matcher<View> = withId(R.id.chatNameInput)
    val chatImageChooser: Matcher<View> = withId(R.id.chatImageChooser)
    val createChatButton: Matcher<View> = withId(R.id.createChatButton)
    val closeButton: Matcher<View> = withId(R.id.closeButton)
    val messagesRecyclerView: Matcher<View> = withId(R.id.messagesRecyclerView)

    private val chatNameEditText: Matcher<View> = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(R.id.chatNameInput))
    )

    private val imageItem: Matcher<View> = allOf(
        isAssignableFrom(ImageChooserItemView::class.java),
        isDescendantOfA(withId(R.id.chatImageChooser))
    )

    fun createdChatTitle(chatName: String): Matcher<View> = allOf(
        withId(DesignR.id.chatNameTextView),
        withText(chatName),
        isDescendantOfA(withId(R.id.chatHeaderView))
    )

    fun openCreateChat() {
        onView(createChatNavigation).waitUntilVisible(10).perform(click())
    }

    fun enterChatName(chatName: String) {
        onView(chatNameEditText).waitUntilVisible(5)
            .perform(replaceText(chatName), closeSoftKeyboard())
    }

    fun selectChatImage(index: Int = 1) {
        onView(chatImageChooser).waitUntilVisible(5).perform(waitUntilRealImagesLoaded())
        onView(withIndex(imageItem, index)).perform(click())
    }

    fun tapCreateChat() {
        onView(createChatButton).waitUntilVisible(5).perform(click())
    }

    fun closeCreatedChat() {
        onView(closeButton).waitUntilVisible(5).perform(click())
    }

    private fun waitUntilRealImagesLoaded(timeoutMillis: Long = 5_000): ViewAction =
        object : ViewAction {
            override fun getConstraints(): Matcher<View> =
                isAssignableFrom(ImageChooserListView::class.java)

            override fun getDescription(): String =
                "wait until real chat images are loaded"

            override fun perform(uiController: UiController, view: View) {
                val chooser = view as ImageChooserListView
                val endTime = System.currentTimeMillis() + timeoutMillis

                while (System.currentTimeMillis() < endTime) {
                    if ((chooser.selectedImage?.id ?: -1) > 0) return
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