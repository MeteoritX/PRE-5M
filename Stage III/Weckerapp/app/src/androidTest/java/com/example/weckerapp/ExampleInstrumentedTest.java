package com.example.weckerapp;

import android.content.Context;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.weckerapp", appContext.getPackageName());
    }

   // @Test void testNightmode(){
   //Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
   //int nightModeFlag = appContext.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
   //assertEquals(nightModeFlag, Configuration.UI_MODE_NIGHT_NO);
   // }
}