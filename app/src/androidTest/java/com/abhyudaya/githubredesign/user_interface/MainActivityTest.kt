package com.abhyudaya.githubredesign.user_interface


import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.abhyudaya.githubredesign.R
import com.abhyudaya.githubredesign.adapter.ContributorViewAdapter
import com.abhyudaya.githubredesign.adapter.RecyclerViewAdapter
import com.abhyudaya.githubredesign.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = launchActivity()
        scenario.moveToState(Lifecycle.State.RESUMED)
        // registering the idling resource
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregister() {
        // unregistering the idling resource
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun testCompleteFlowForValidUserName() {
        val userName = "greenrobot"
        onView(withId(R.id.username)).perform(typeText(userName))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.button)).perform(click())

        // check if recycler view is displayed
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))

        // clicking the recycler view item
        onView(withId(R.id.recycler_view))
            .perform(actionOnItemAtPosition<RecyclerViewAdapter.ViewHolder>(0, click()))

        // checking if the contributor list is displayed
        onView(withId(R.id.contributor_view)).check(matches(isDisplayed()))

        // clicking on the contributor list
        onView(withId(R.id.contributor_view))
            .perform(actionOnItemAtPosition<ContributorViewAdapter.ViewHolder>(0, click()))

        // checking if again profile is displayed
        onView(withId(R.id.repositories_data)).check(matches(isDisplayed()))
    }

    @Test
    fun testCompleteFlowWithInvalidUserName() {
        val userName = "SomeInvalidUserName"
        onView(withId(R.id.username)).perform(typeText(userName))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.button)).perform(click())

        // checks if User Not Found is Displayed
        onView(withId(R.id.user_not_found_text)).check(matches(isDisplayed()))
    }

    @Test
    fun testInput() {
        val userName = "greenrobot"
        onView(withId(R.id.username)).perform(typeText(userName))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.button)).perform(click())
        onView(withText("@$userName")).check(matches(isDisplayed()))
    }

    @Test
    fun testRepositories() {
        val userName = "greenrobot"
        onView(withId(R.id.username)).perform(typeText(userName))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.repositories_data)).check(matches(isDisplayed()))
    }
}
