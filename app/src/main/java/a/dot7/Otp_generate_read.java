package a.dot7;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.msg91.sendotp.library.SendOtpVerification;
import com.msg91.sendotp.library.Verification;
import com.msg91.sendotp.library.VerificationListener;

import java.util.Random;

import android.content.Context;
import android.content.Intent;

import a.common.Broadcast_Listener;
import a.common.MyDialog;
import a.common.OTP_Generator;
import a.common.Permissions;
import a.common.SMS_broadcastReciever;
import a.common.Services;
import a.common.SmsListener;
import a.common.SmsReceiver;

import android.util.Log;
import android.widget.Toast;

/**
 * Created by Suman and Harneet on 17-03-2018.
 */

public class Otp_generate_read extends AppCompatActivity implements VerificationListener {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    String OTP = null;
    Intent intent = null;
    private String Name=null, Contact=null, Password=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_generate_read);
        intent = getIntent();
        String determine_activity = null;
             //  determine_activity = intent.getStringExtra("ForgotPassword");
        Name = intent.getStringExtra("Name");
        Contact = intent.getStringExtra("Contact");
        Password = intent.getStringExtra("Password");
        OTP = intent.getStringExtra("OTP");
        //getDetails();
        //sendMessage();
        if (determine_activity == null)
            register_work();
        else
            forgot_password();
    }

    public void new_password(View view)
    {
        //startActivity(new Intent(this,setnew_password.class));
    }

    private void forgot_password() {

        read();
        //intent to enter new password; where new password is stored in local directory and send to server also
    }

    private void register_work() {
        if (read())
        {


            boolean Status = Services.getInstance(this).Register(Name,Password,Contact);

            if (Status) {
                createLocalFile(Name,Password,Contact);
                Toast.makeText(this, "User Registered succesfully", Toast.LENGTH_SHORT).show();
                /*Intent intent=new Intent(this,RestaurantView.class);
                startActivity(intent);*/
            }
            else {
                MyDialog myDialog=new MyDialog(this,"Something went wrong! Try Again","OK");
                startActivity(new Intent(this,Register.class));
            }
        }
    }

    private void createLocalFile(String name, String password, String contact) {
        SharedPreferences sharedPreferences=getSharedPreferences("logDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Name",name);
        editor.putString("Password",password);
        editor.putString("UserName",contact);
        editor.commit();
    }



    public boolean read() {

        final boolean[] flag = new boolean[1];
        //final boolean[] Flag = {false};
        Permissions.getInstance(this).checkpermissions(this, REQUEST_ID_MULTIPLE_PERMISSIONS);
        if (true)
        {
            Log.d("HAR", "Final state success permission mil gyi");

            SmsReceiver.bindListener(new SmsListener() {
                @Override
                public void messageReceived(String message) {
                    String Message = intent.getStringExtra("message");
                    String OTP_Received = Message.substring(27, 30);
                    if(OTP_Received.equals(OTP))
                    {
                        Log.e("HAR", "Final state success,OTP verified, otp: "+OTP_Received);
                        flag[0] = true;
                    }
                    else
                    {

                    }
                }


            });




          /*  SMS_broadcastReciever.bindListener(new Broadcast_Listener()
            {
                @Override
                public void message_Received(String message, Intent intent)
                {
                    if (intent.getAction().equalsIgnoreCase("otp"))
                    {
                        String msg = intent.getStringExtra("message");
                        String OTP_Received = msg.substring(27, 30);
                        //suman, you will always get the code with body as:
                        //Your verification code is ****
                        //write suitable substring function
                        if (OTP_Received.contentEquals(OTP)) {
                            Log.e("HAR", "Final state success, otp: "+OTP_Received);
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
            */

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
         Log.d("HAR","Response not Success with mesage "+paramException.toString());
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








   /* private String Generate() {
        Random generate = new Random();
        char[] otp = new char[4];
        for (int i = 0; i < 4; i++) {
            int x = generate.nextInt(10);
            otp[i] = (char) (x + '0');
        }
        String Message = String.valueOf(otp);
        //final String URL = "http://172.31.143.78:3000/OTP";
        //boolean code = Services.getInstance(this).otp_service_call(Message, URL,this);
        return Message;
    }
    private void sendMessage()
    {
        Verification Verify = SendOtpVerification.createSmsVerification(
                SendOtpVerification
                        .config("91"+"9711919938")  //specify the mobile number ID here
                        .context(this)
                        .autoVerification(true)
                        .expiry("2")
                        .senderId("DOTSMS")
                        .otp(OTP)
                        .otplength("4")
                        .build(),this);

        Verify.initiate();
    }


    */