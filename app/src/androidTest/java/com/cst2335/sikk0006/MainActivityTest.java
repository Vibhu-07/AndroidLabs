package com.cst2335.sikk0006;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    /**
     * Object from mainActivity class that calls the user defined methods to check for test cases
     */
    private MainActivity activity;

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * This test case checks that the password input fails the test if conditions doesn't match
     */
    @Test
    public void mainActivityTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText = onView(withId(R.id.editTextTextPersonName));
        appCompatEditText.perform(replaceText("12345"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * This test case checks that the password input fails the test if it does not contain an uppercase letter.
     */
    @Test
    public void testFindMissingUpperCase () {

        ViewInteraction appCompatEditText = onView(withId(R.id.editTextTextPersonName));
        appCompatEditText.perform (replaceText("password1234")) ;

        ViewInteraction materialButton = onView(withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches (withText ("You shall not pass!")));
    }

    /**
     * This test case checks that the password input fails the test if it does not contain an uppercase letter.
     */
    @Test
    public void testPasswordCheckNoDigit() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editTextTextPersonName));
        appCompatEditText.perform (replaceText("#Password")) ;

        ViewInteraction materialButton = onView(withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches (withText ("You shall not pass!")));
    }

    /**
     * This test case checks that the password input fails the test if it does not contain an uppercase letter.
     */
    @Test
    public void testPasswordCheckNoUppercase() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editTextTextPersonName));
        appCompatEditText.perform (replaceText("#password1223")) ;

        ViewInteraction materialButton = onView(withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches (withText ("You shall not pass!")));
    }

    /**
     * This test case checks that the password input fails the test if it does not contain a lowercase letter.
     */
    @Test
    public void testPasswordCheckNoLowercase() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editTextTextPersonName));
        appCompatEditText.perform (replaceText("#PASSWORD1234")) ;

        ViewInteraction materialButton = onView(withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches (withText ("You shall not pass!")));
    }

    /**
     * This test case checks that the password input fails the test if it does not contain a special character.
     */
    @Test
    public void testPasswordCheckNoSpecial() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editTextTextPersonName));
        appCompatEditText.perform (replaceText("#password1234")) ;

        ViewInteraction materialButton = onView(withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches (withText ("You shall not pass!")));
    }

    /**
     * This test case checks that the password input fails the test if it is too short.
     */
    @Test
    public void testPasswordCheckTooShort() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editTextTextPersonName));
        appCompatEditText.perform (replaceText("#pass")) ;

        ViewInteraction materialButton = onView(withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches (withText ("You shall not pass!")));
    }

    /**
     * This test case checks that the password input passes the test if it meets all the password requirements.
     */
    @Test
    public void testPasswordCheckPass() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editTextTextPersonName));
        appCompatEditText.perform (replaceText("#Password1234")) ;

        ViewInteraction materialButton = onView(withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches (withText ("Your password meets the requirements")));
    }
}
