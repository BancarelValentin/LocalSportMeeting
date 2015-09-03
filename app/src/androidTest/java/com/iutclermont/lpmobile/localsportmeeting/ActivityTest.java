package com.iutclermont.lpmobile.localsportmeeting;

import android.graphics.Point;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.Gravity;

import com.robotium.solo.Solo;

/**
 * Created by Thomas on 17/12/2014.
 */
public class ActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    DrawerLayout mDrawerLayout;

    public ActivityTest () {
        this(MainActivity.class);
    }

    public ActivityTest(Class<MainActivity> activityClass) {
        super(activityClass);
    }

    @SmallTest
    public void testNavigationDrawer () {
        while (!getActivity().mainFragLoaded);
        Point deviceSize = new Point();
        Solo s = new Solo (getInstrumentation(), getActivity());
        s.getCurrentActivity().getWindowManager().getDefaultDisplay().getSize(deviceSize);

        int screenWidth = deviceSize.x;
        int screenHeight = deviceSize.y;
        int fromX = 0;
        int toX = screenWidth / 2;
        int fromY = screenHeight / 2;
        int toY = fromY;

        s.drag(fromX, toX, fromY, toY, 20);
        s.sleep(10000);
        mDrawerLayout = (DrawerLayout)getActivity().findViewById(R.id.drawerLayout);
        assertTrue(mDrawerLayout.isDrawerOpen(GravityCompat.START));
        s.clickOnView(mDrawerLayout.getChildAt(0));

        s.sleep(50000);

        s.drag(fromX, toX, fromY, toY, 100);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mDrawerLayout = (DrawerLayout)getActivity().findViewById(R.id.drawerLayout);
    }
}

