package a.dot7;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.msg91.sendotp.library.VerificationListener;

import java.util.Random;

import android.content.Context;
import android.content.Intent;

import a.common.Broadcast_Listener;
import a.common.MyDialog;
import a.common.Permissions;
import a.common.SMS_broadcastReciever;
import a.common.Services;

import android.util.Log;

/**
 * Created by Suman and Harneet on 17-03-2018.
 */

public class Otp_generate_read extends AppCompatActivity implements VerificationListener {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    String OTP = Generate();
    Intent intent=getIntent();
    private String Name, Contact, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_generate_read);
        String determine_activity = intent.getStringExtra("ForgotPassword");
        if (determine_activity == null)
            register_work();
        else
            forgot_password();
    }

    private void forgot_password() {

        read();
        //intent to enter new password; where new password is stored in local directory and send to server also
    }

    private void register_work() {
        if (read())
        {
            Name=intent.getStringExtra("Name");
            Contact=intent.getStringExtra("Contact");
            Password=intent.getStringExtra("Password");

            boolean Status = Services.getInstance(this).Register(Name,Password,Contact);

            if (Status) {
                createLocalfile(Name,Password,Contact);
                /*Intent intent=new Intent(this,RestaurantView.class);
                startActivity(intent);*/
            }
            else {
                MyDialog myDialog=new MyDialog(this,"Something went wrong! Try Again","OK");
                startActivity(new Intent(this,Register.class));
            }
        }
    }

    private void createLocalfile(String name, String password, String contact) {
        SharedPreferences sharedPreferences=getSharedPreferences("logDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Name",name);
        editor.putString("Password",password);
        editor.putString("UserName",contact);
        editor.commit();
    }

    private String Generate() {
        Random generate = new Random();
        char[] otp = new char[4];
        for (int i = 0; i < 4; i++) {
            int x = generate.nextInt(10);
            otp[i] = (char) (x + '0');
        }
        String Message = String.valueOf(otp);
        final String URL = "http://172.31.143.78:3000/OTP";
        boolean code = Services.getInstance(this).otp_service_call(Message, URL,this);
        return Message;
    }

    public boolean read() {

        final boolean[] flag = new boolean[1];
        if (Permissions.getInstance(this).checkpermissions(this, REQUEST_ID_MULTIPLE_PERMISSIONS))
        {
            Log.d("HAR", "Final state success");
            SMS_broadcastReciever.bindListener(new Broadcast_Listener()
            {
                @Override
                public void message_Received(String message, Intent intent)
                {
                    if (intent.getAction().equalsIgnoreCase("otp"))
                    {
                        String msg = intent.getStringExtra("message");
                        msg = msg.substring(27, 30);
                        //suman, you will always get the code with body as:
                        //Your verification code is ****
                        //write suitable substring function
                        if (msg.equals(OTP)) {
                            Log.e("HAR", "Final state success");
                            flag[0] = true;

                        }
                    }
                    else {
                        Log.e("HAR", "Final state fail");
                        flag[0] = false;
                        //otp_message.setText("Error Validating Phone No. \n Resend Code!");
                        //pooja and nitish add alert or something
                    }
                }
            });

        }

        else
        {
            MyDialog myDialog = new MyDialog(this,"Cannot access OTP without" +
                    " permissions!","Grant Permissions");
            read();
        }
        return flag[0];
    }


    //  MANUAL VERIFICATION

            /*Verification Verify = SendOtpVerification.createSmsVerification(
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
            Verify.verify(OTP);  //Manual verification, not auto verification.*/


    @Override
    public void onInitiated(String response) {
        Log.d("HAR", "Response Success with mesage " + response);
    }

    @Override
    public void onInitiationFailed(Exception paramException) {
        // Log.d("HAR","Response Success with mesage "+paramException.toString());
        //Nitish and pooja show required error message if message is not sent
    }

    @Override
    public void onVerified(String response) {
        Log.d("HAR", "Verify Success with mesage " + response);
        //OTP verified Success
        //Redirect to Restaurant View
    }

    @Override
    public void onVerificationFailed(Exception paramException) {
        Log.d("HAR", "Error In verification " + paramException.toString());
        //Nitish and pooja show suitable alert box if otp not verified or otp is expired i.e 2 mins
    }

}
