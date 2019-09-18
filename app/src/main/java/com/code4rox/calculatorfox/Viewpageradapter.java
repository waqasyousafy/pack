package com.code4rox.calculatorfox;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Viewpageradapter extends FragmentPagerAdapter {
    private Context mcontext;
    private String[] tabTitles = new String[]{"Chat", "Status", "Calls"};
    public Viewpageradapter(Context context, FragmentManager fm) {
        super(fm);
        mcontext=context;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0)
        {
            return new chat();
        }
        else if(position==1)
        {
            return  new status();
        }
        else{
            return new calls();
        }

    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }
}
