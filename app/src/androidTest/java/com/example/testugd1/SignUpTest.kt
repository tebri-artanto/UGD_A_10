package com.example.testugd1


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.GrantPermissionRule
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SignUpTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(SignUp::class.java)

    @Test
    fun signUpTest() {
        val materialButton = onView(
            allOf(
                withId(R.id.btnSignUp), withText("SignUp"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout),
                        childAtPosition(
                            withId(R.id.singUpLayout),
                            2
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val textInputEditText = onView(
            allOf(
                withId(R.id.input_username),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutUsername),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("123"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.input_password),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutPassword),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("123"), closeSoftKeyboard())

        val materialButton2 = onView(
            allOf(
                withId(R.id.btnSignUp), withText("SignUp"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout),
                        childAtPosition(
                            withId(R.id.singUpLayout),
                            2
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.input_email),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutEmail),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText3.perform(replaceText("123@gmail.com"), closeSoftKeyboard())

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.input_tanggalLahir),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutTanggalLahir),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(replaceText("2000"), closeSoftKeyboard())

        val textInputEditText5 = onView(
            allOf(
                withId(R.id.input_noTelpon),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutNoTelpon),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText5.perform(replaceText("123456"), closeSoftKeyboard())

        val materialButton3 = onView(
            allOf(
                withId(R.id.btnSignUp), withText("SignUp"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout),
                        childAtPosition(
                            withId(R.id.singUpLayout),
                            2
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
