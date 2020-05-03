package com.example.PetFind;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.android21buttons.fragmenttestrule.FragmentTestRule;

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
public class FeedTest {
    @Rule
    public FragmentTestRule<MainActivity, SearchFragment> fragmentTestRule =
            new FragmentTestRule<>(MainActivity.class, SearchFragment.class);
    @Test
    public void testFeed(){
        Espresso.onView(ViewMatchers.withId(R.id.searchFilter)).
                perform(ViewActions.typeText("Lizard"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.searchFilter)).
                perform(ViewActions.typeText("Cat"));
    }
}
