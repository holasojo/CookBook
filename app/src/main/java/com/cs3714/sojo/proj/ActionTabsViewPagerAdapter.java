package com.cs3714.sojo.proj;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.ArrayList;


public class ActionTabsViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;

    public static final int CHAT = 0;
    public static final int FIND = 1;
    public static final int MEET = 2;
    public static final String UI_TAB_CHAT = "INGREDIENTS";
    public static final String UI_TAB_FIND = "LIST";
    public static final String UI_TAB_MEET = "RECIPE";

    public ActionTabsViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments){
        super(fm);
        this.fragments = fragments;
    }

    public Fragment getItem(int pos){
        return fragments.get(pos);
    }

    public int getCount(){
        return fragments.size();
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case CHAT:
                return UI_TAB_CHAT;
            case FIND:
                return UI_TAB_FIND;
            case MEET:
                return UI_TAB_MEET;

            default:
                break;
        }
        return null;
    }
}
