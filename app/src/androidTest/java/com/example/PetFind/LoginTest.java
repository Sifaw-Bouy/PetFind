package com.example.PetFind;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest {
    @Rule
    public ActivityTestRule<Login> Rule =
            new ActivityTestRule<>(Login.class);
    @Test
    public void testLogin(){
        Espresso.onView(ViewMatchers.withId(R.id.EmailLogin)).
                perform(ViewActions.typeText("test@gmail.com"));
        Espresso.onView(ViewMatchers.withId(R.id.LoginPass)).
                perform(ViewActions.typeText("123456"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.LoginBtn)).
                perform(ViewActions.click());
    }
}
