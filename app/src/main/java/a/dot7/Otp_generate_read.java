package a.dot7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import java.util.ArrayList;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.msg91.sendotp.library.SendOtpVerification;
import com.msg91.sendotp.library.Verification;
import com.msg91.sendotp.library.VerificationListener;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import android.support.v4.content.LocalBroadcastManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import a.common.GlobalMethods;
import a.common.MyDialog;
import a.common.MySingleton;
import a.common.Permissions;
import a.common.Services;

import android.content.BroadcastReceiver;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Suman and Harneet on 17-03-2018.
 */

public class Otp_generate_read extends AppCompatActivity implements VerificationListener {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    String OTP = Generate();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_generate_read);
        read();
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        //TextView otp_message = (TextView) findViewById(R.id.txtview);
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equalsIgnoreCase("otp")) {
                String message = intent.getStringExtra("message");
               // message=message.substring(26,29);         //specify index of code in the message
                //suman, you will always get the code with body as:
                //Your verification code is ****
                //write suitable substring function
                if(message.equals(OTP))
                {
                    Log.d("HAR","Final state success");
                    // intent to restaurant view

                }
            }
            else
            {
                Log.d("HAR","Final state fail");
                //otp_message.setText("Error Validating Phone No. \n Resend Code!");
                //pooja and nitish add alert or something
            }
        }
    };

    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private String Generate() {
        Random generate=new Random();
        char[] otp=new char[4];
        for (int i=0;i<4;i++)
        {
            int x=generate.nextInt(10);
            otp[i]=(char)(x+'0');
        }
        return String.valueOf(otp);

    }

    private void read() {

        if (Permissions.getInstance(this).checkpermissions(this,REQUEST_ID_MULTIPLE_PERMISSIONS))
        {

            Verification Verify = SendOtpVerification.createSmsVerification(
                    SendOtpVerification
                            .config("91"+"9039216432")  //specify the mobile number ID here
                            .context(this)
                            .autoVerification(true)
                            .expiry("2")
                            .senderId("DOTSMS")
                            .otp(OTP)
                            .otplength("4")
                            .build(),this);

            Verify.initiate();
            Verify.verify(OTP);  //Manual verification, not auto verification



            //some problem here, Suman look into this.

        }
       /* else
        {
            MyDialog myDialog=new MyDialog(this,"Cannot access OTP without" +
                    " permissions!","Grant Permissions");
            Permissions.getInstance(this).checkpermissions(this,REQUEST_ID_MULTIPLE_PERMISSIONS);
        }*/
    }

    @Override
    public void onInitiated(String response) {
        Log.d("HAR","Response Success with mesage "+response);
    }

    @Override
    public void onInitiationFailed(Exception paramException) {
       // Log.d("HAR","Response Success with mesage "+paramException.toString());
        //Nitish and pooja show required error message if message is not sent
    }

    @Override
    public void onVerified(String response) {
        Log.d("HAR","Verify Success with mesage "+response);
        //OTP verified Success
        //Redirect to Restaurant View
    }

    @Override
    public void onVerificationFailed(Exception paramException) {
       Log.d("HAR","Error In verification "+paramException.toString());
        //Nitish and pooja show suitable alert box if otp not verified or otp is expired i.e 2 mins
    }

}
