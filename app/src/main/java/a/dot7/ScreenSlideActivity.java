package a.dot7;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import a.fragments.ScreenSlidePage_Fragment;
import a.fragments.ScreenSlidePage_Fragment2;
import a.fragments.ScreenSlidePage_Fragment3;

public class ScreenSlideActivity extends FragmentActivity {

    private static int Min_Pages=3;

private ViewPager mpager;
private PagerAdapter mpageradapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);
        mpager=findViewById(R.id.pager);
        mpageradapter=new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mpager.setAdapter(mpageradapter);

    }

    public void login(View view)
    {
        startActivity(new Intent(this,MainActivity.class));
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter
    {
        public ScreenSlidePagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:  return new ScreenSlidePage_Fragment();
                case 1: return  new ScreenSlidePage_Fragment2();
                case 2: return new ScreenSlidePage_Fragment3();
                default: return new ScreenSlidePage_Fragment();
            }
        }

        @Override
        public int getCount() {
            return Min_Pages;
        }
    }

}

