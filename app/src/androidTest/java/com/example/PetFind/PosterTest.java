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
public class PosterTest {
    @Rule
    public FragmentTestRule<MainActivity, PosterFragment> fragmentTestRule =
            new FragmentTestRule<>(MainActivity.class, PosterFragment.class);
    @Test
    public void testPoster(){
        Espresso.onView(ViewMatchers.withId(R.id.speciesID)).
                perform(ViewActions.typeText("lizard"));
        Espresso.onView(ViewMatchers.withId(R.id.petNameID)).
                perform(ViewActions.typeText("sammy"));
        Espresso.onView(ViewMatchers.withId(R.id.statResID)).
                perform(ViewActions.typeText("arizona"));
        Espresso.onView(ViewMatchers.withId(R.id.descripID)).
                perform(ViewActions.typeText("lost please help find"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.sendTFiler)).
                perform(ViewActions.click());
    }
}
