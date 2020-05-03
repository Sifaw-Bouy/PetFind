package com.example.PetFind;

import android.content.Context;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegisterTest {
    @Rule
    public ActivityTestRule<Register> Rule =
            new ActivityTestRule<>(Register.class);
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.PetFind", appContext.getPackageName());
    }
    @Test
    public void testRegistration(){
        Espresso.onView(ViewMatchers.withId(R.id.UserEmail)).
                perform(ViewActions.typeText("test@gmail.com"));
        Espresso.onView(ViewMatchers.withId(R.id.UserName)).
                perform(ViewActions.typeText("testuser"));
        Espresso.onView(ViewMatchers.withId(R.id.Password)).
                perform(ViewActions.typeText("123456"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.Register_button)).
                perform(ViewActions.click());
    }
}
