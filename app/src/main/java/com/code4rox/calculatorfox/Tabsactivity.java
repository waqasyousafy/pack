package com.code4rox.calculatorfox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class Tabsactivity extends AppCompatActivity implements chat.OnFragmentInteractionListener,status.OnFragmentInteractionListener,calls.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabsactivity);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        Viewpageradapter adapter = new Viewpageradapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sibling_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
