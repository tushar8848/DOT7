package a.dot7;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import a.common.CheckConnection;
import a.common.LoginCheck;
import a.common.ValidateUserCredentials;
import a.getter_setter.UserCredentials;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getNetworkState();
                //startActivity(new Intent(SplashActivity.this,ScreenSlideActivity.class));
                finish();
            }
        },SPLASH_TIME_OUT);

    }
    private void getNetworkState()
    {
        UserCredentials credentials;
        Boolean State = CheckConnection.getInstance(this).getNetworkStatus();
        if(State)  //internet is connected
        {
            ValidateUserCredentials.getInstance(this).Validate("Tushar2897","12345");

            credentials = LoginCheck.getInstance(this).CheckUserCredentials();
            if(credentials == null)  //if login file is missing or credentials were changed
            {
                startActivity(new Intent(this,ScreenSlideActivity.class));
            }
            else
            {

                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
