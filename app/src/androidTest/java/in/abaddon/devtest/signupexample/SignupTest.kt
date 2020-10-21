package `in`.abaddon.devtest.signupexample

import `in`.abaddon.devtest.signupexample.signup.SignupActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SignupTest {
    @get:Rule
    var mActivityRule: ActivityScenarioRule<SignupActivity> = ActivityScenarioRule(SignupActivity::class.java)

    @Test
    fun typeInvalidName() {
        onView(withId(R.id.field_name)).perform(typeText("   "))

        Thread.sleep(800) // there is a debouncer

        onView(withText(R.string.msg_is_required)).check(matches(isDisplayed()))
    }

    @Test
    fun typeInvalidEmail() {
        onView(withId(R.id.field_email)).perform(typeText("foo@"))

        Thread.sleep(800) // there is a debouncer

        onView(withText(R.string.msg_email_invalid)).check(matches(isDisplayed()))
    }

    @Test
    fun typeInvalidBirthday() {
        onView(withId(R.id.field_birthday)).perform(typeText("29.2.2003"))

        Thread.sleep(800) // there is a debouncer

        onView(withText(R.string.msg_date_invalid)).check(matches(isDisplayed()))
    }

    @Test
    fun testIfSubmitIsEnabled() {
        onView(withId(R.id.submit_button)).check(matches(not(isEnabled())))

        onView(withId(R.id.field_name)).perform(typeText("Felix Muster"))
        onView(withId(R.id.field_email)).perform(typeText("foo@muster.ch"))
        onView(withId(R.id.field_birthday)).perform(typeText("28.2.2003"))

        Thread.sleep(800) // there is a debouncer

        onView(withId(R.id.submit_button)).check(matches(isEnabled()))
    }
}
