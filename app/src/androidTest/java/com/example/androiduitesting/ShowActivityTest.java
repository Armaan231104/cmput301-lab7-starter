package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ShowActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void initIntents() {
        Intents.init();
    }

    @After
    public void releaseIntents() {
        Intents.release();
    }

    private void addCity(String name) {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.replaceText(name), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.button_confirm)).perform(click());
    }

    private void openFirstCity() {
        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());
    }

    @Test
    public void clickingListItem_opensShowActivity() {
        addCity("Edmonton");
        openFirstCity();
        intended(hasComponent(ShowActivity.class.getName()));
        onView(withId(R.id.text_city_name)).check(matches(isDisplayed()));
    }

    @Test
    public void selectedCityName_isShown() {
        addCity("Edmonton");
        openFirstCity();
        onView(withId(R.id.text_city_name)).check(matches(withText("Edmonton")));
    }

    @Test
    public void backButton_returnsToMainActivity() {
        addCity("Edmonton");
        openFirstCity();
        onView(withId(R.id.button_back)).perform(click());
        onView(withId(R.id.city_list)).check(matches(isDisplayed()));
    }
}