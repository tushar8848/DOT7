package a.dot7;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.provider.Settings;

import a.Lifecycle_Restaurant_Ordering.Restaurant_Main_Page;
import a.common.GlobalMethods;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;

   // String url = "172.31.143.34:3000//Login";
    String StatusCode;
    private String UserName,Password;
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
      //  Boolean State = CheckConnection.getInstance(this).getNetworkStatus();
        Boolean State = true;
        if(State)  //internet is connected
        {
            CheckUserCredentials();

        }
        else
        {
            GlobalMethods.print(this,"Please open internet connection");
            Intent callGPSSettingIntent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
            startActivity(callGPSSettingIntent);
        }
    }

    public void CheckUserCredentials()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("logDetails",
                Context.MODE_PRIVATE);
        UserName = sharedPreferences.getString("UserName",null);
        Password = sharedPreferences.getString("Password",null);
        Log.d("HAR","Username:"+UserName+" Password:"+Password);
        if(UserName != null && Password != null) {
            // Services.getInstance(context).Validate(UserName,Password);
            startActivity(new Intent(SplashActivity.this, Restaurant_Main_Page.class));

        }
        else
        {
            Log.d("HAR","Shared preference not found");
            startActivity(new Intent(SplashActivity.this, ScreenSlideActivity.class));
        }
    }
}
/*
 try {
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.
                        Listener<String>() {

                    @Override
                    public void onResponse(String s) {
                        StatusCode = GlobalMethods.GetSubString(s);
                        Log.d("HAR", s);
                        Log.d("HAR", "Satus code:" + StatusCode);
                        //Log.d("HAR",StatusCode);

                        // **********stop progress bar*************************


                        if (StatusCode.contains("302")) {
                           // GlobalMethods.print(SplashActivity.this, "Data Found");
                           // Intent intent = new Intent(SplashActivity.this, TempActivity.class);
                            startActivity(new Intent(SplashActivity.this, Restaurant_Main_Page.class));


                        } else {
                            startActivity(new Intent(SplashActivity.this, ScreenSlideActivity.class));

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("HAR", volleyError.toString());
                        Log.d("HAR", "Error");
                        //Nitish and pooja, handle this error with a alert box or something
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<>();
                        parameters.put("LoginID", UserName);
                        parameters.put("password", Password);
                        // if(Name != null)
                        //   parameters.put("Name",Name);
                        return parameters;
                    }
                };
                MySingleton.getInstance(this).addToRequestQueue(request);

                Log.d("HAR", "Service ab return kr ri hai");

            } catch (Exception ex) {

            }

 */