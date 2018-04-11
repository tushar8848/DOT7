package a.dot7;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * Created by nitis on 4/12/2018.
 */

public class AboutUsTemp extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us_temp);

        Toolbar toolbar = findViewById(R.id.devPageTempToolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


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
}
