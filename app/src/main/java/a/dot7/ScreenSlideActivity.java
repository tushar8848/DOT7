package a.dot7;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

import a.FirebasePushNotification.MyFirebaseInstanceIDService;
import a.common.MySingleton;
import a.fragments.ScreenSlidePage_Fragment;
import a.fragments.ScreenSlidePage_Fragment2;
import a.fragments.ScreenSlidePage_Fragment3;

public class ScreenSlideActivity extends FragmentActivity {

    private static int Min_Pages = 3;

    private ViewPager mpager;
    private PagerAdapter mpageradapter;
    LinearLayout sliderDotsPanel;
    private int dotsCount;
    MyFirebaseInstanceIDService f = new MyFirebaseInstanceIDService();
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);
        mpager = findViewById(R.id.Pager);
        mpageradapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mpager.setAdapter(mpageradapter);
        sliderDotsPanel = findViewById(R.id.SliderDots);
        dotsCount = Min_Pages;
        dots = new ImageView[dotsCount];

//        f.refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //      f.sendRegistrationToServer(f.refreshedToken,ScreenSlideActivity.this);

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(0, 8, 20, 16);
            sliderDotsPanel.addView(dots[i], params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
        mpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int pos;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                pos = position;
                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void login(View view)

    {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void GoRegister(View view) {
        startActivity(new Intent(this, Register.class));
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            int index = position % 3;
            switch (index) {
                case 0:
                    return new ScreenSlidePage_Fragment();
                case 1:
                    return new ScreenSlidePage_Fragment2();
                case 2:
                    return new ScreenSlidePage_Fragment3();
                default:
                    return new ScreenSlidePage_Fragment();
            }
        }


        @Override
        public int getCount() {
            return Min_Pages;
        }
    }


}

