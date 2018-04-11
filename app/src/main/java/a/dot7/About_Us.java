package a.dot7;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import a.fragments.DevFrag1;
import a.fragments.DevFrag2;
import a.fragments.DevFrag3;
import a.fragments.DevFrag4;
import a.fragments.DevFrag5;
import a.fragments.DevFrag6;
import a.getter_setter.DeveloperDetails;

public class About_Us extends AppCompatActivity {

    ViewPager viewPager;
    ViewPagerAdapter adapter;
    DevFrag1 devFrag1;
    DevFrag2 devFrag2;
    DevFrag3 devFrag3;
    DevFrag4 devFrag4;
    DevFrag5 devFrag5;
    DevFrag6 devFrag6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about__us);
        //setContentView(R.layout.place_order);
        Toolbar toolbar = findViewById(R.id.devPageToolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.devPager);
        Log.d("HAR","About us takk aaya details fill ho gyi");
        addFrag();
        setupViewPager(viewPager);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    private void setupViewPager(ViewPager viewPager) {

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                // mDrawerLayout.openDrawer(GravityCompat.START);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void addFrag() {
        devFrag1 = new DevFrag1();
        devFrag2 = new DevFrag2();
        devFrag3 = new DevFrag3();
        devFrag4 = new DevFrag4();
        devFrag5 = new DevFrag5();
        devFrag6 = new DevFrag6();
    }
    class ViewPagerAdapter extends FragmentStatePagerAdapter {



        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
            Log.d("HAR","ViewPAGERADAPTER takk aaya");
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 1:return devFrag1;
                case 2:return devFrag2;

                case 3:return devFrag3;

                case 4:return devFrag4;

                case 5:return devFrag5;

                case 6:return devFrag6;

                    default: return devFrag1;

            }

        }

        @Override
        public int getCount() {
            return 6;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }


    }


}

