package com.flapjack.FlapJackAndroid;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * User: AnubhawArya
 * Date: 9/14/13
 * Time: 2:49 PM
 */
public class StatPagerAdapter extends FragmentStatePagerAdapter {
    public StatPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public Fragment getItem(int i) {
        if (i == 1)
            return new HashMapFragment();
        else
            return new GraphFragment();

    }

    public int getCount() {
        return 2;
    }
}
