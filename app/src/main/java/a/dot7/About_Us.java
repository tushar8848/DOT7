package a.dot7;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class About_Us extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about__us);
        //setContentView(R.layout.place_order);
        Toolbar toolbar = findViewById(R.id.devPageToolbar);
        setSupportActionBar(toolbar);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.devPager);
        setupViewPager(viewPager);
        if (getSupportActionBar() != null){
           getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        DevDummyFragment devDummyFragment;
        devDummyFragment = new DevDummyFragment();
        devDummyFragment.setFragmentArguments("Tushar Garg","BackEnd Developer",R.drawable.tushar_thumb);

        adapter.addFrag(devDummyFragment);
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
        class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> FragmentList = new ArrayList<>(6);
        private final List<String> FragmentTitleList = new ArrayList<>(6);

        public ViewPagerAdapter(FragmentManager manager){super(manager);}

        @Override
        public Fragment getItem(int position) {
            return FragmentList.get(position);
        }

        @Override
        public int getCount() {
            return FragmentList.size();
        }

        public void addFrag(Fragment fragment, String title,int imageID){
            FragmentList.add(fragment);
            FragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return FragmentTitleList.get(position);
        }

            public void addFrag(DevDummyFragment devDummyFragment) {
                FragmentList.add(devDummyFragment);
             //   FragmentTitleList.add(title);
            }
        }
    public static class DevDummyFragment extends Fragment
    {
        String name,title;
        TextView Name,Title;
        ImageView imageView;
        int imageId;
        public DevDummyFragment()
        {

        }
        public void setFragmentArguments(String name, String title, int tushar_thumb)
        {
            this.name = name;
            this.title = title;
            this.imageId = tushar_thumb;
        }
        @Override
        public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            View view = inflater.inflate(R.layout.fragment_nitish,container,false);
            Name = view.findViewById(R.id.dev_name);
            Title = view.findViewById(R.id.devTitle);
            imageView = view.findViewById(R.id.dev_thumbnail);
            imageView.setImageResource(imageId);
            Name.setText(name);
            Title.setText(title);
            return view;

        }

    }

}
