package a.common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import a.dot7.R;
import a.dot7.Register;

/**
 * Created by TUSHAR on 28-03-18.
 */

public class OTP_Reader extends AppCompatActivity {
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    String OTP = null;
    Intent intent = null;
    private String Name=null, Contact=null, Password=null;
    boolean Flag = false;

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
        if (determine_activity == null)
            initiateRegister();
        else
        {
            // forgot_password();
        }

    }

    private void initiateRegister() {
        Log.d("HAR", "register_work me aaya");
        read();
        Log.d("HAR", "wapas register work me aaya");
    }
    private void finalRegister()
    {
        if (Flag)
        {

            Log.d("HAR", "OTP is verified");
            Toast.makeText(this, "OTP Verified Succesfully", Toast.LENGTH_SHORT).show();
            boolean Status = Services.getInstance(this).Register(Name,Password,Contact);

            if (Status) {
                createLocalFile();
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

    public void read() {

        Log.d("HAR","read wale me aaya");
        //final boolean[] flag = {false};
        //final boolean[] Flag = {false};
        Permissions.getInstance(this).checkpermissions(this, REQUEST_ID_MULTIPLE_PERMISSIONS);
        if (true)
        {
            Log.d("HAR", "Final state success permission mil gyi");

            SmsReceiver.bindListener(new SmsListener() {
                @Override
                public void messageReceived(String message) {
                    Log.d("HAR", "Reader pe message is:"+message);
                    String OTP_Received = null;
                    OTP_Received = message.substring(26, 30);
                    Log.d("HAR", "OTP is:"+OTP_Received);
                    if(OTP_Received.equals(OTP))
                    {
                        Log.d("HAR", "Final state success,OTP verified, otp: "+OTP_Received);
                        Flag = true;
                        //flag[0] = true;
                    }
                    else
                    {
                            //Here OTP entered is wrong, handle the case
                    }
                  finalRegister();
                }


            });
           // Log.d("HAR","return value: "+flag[0]);

        }

        else
        {
            MyDialog myDialog = new MyDialog(this,"Cannot access OTP without" +
                    " permissions!","Grant Permissions");
            read();
        }
    }
    private void createLocalFile() {
        SharedPreferences sharedPreferences=getSharedPreferences("logDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Name",Name);
        editor.putString("Password",Password);
        editor.putString("UserName",Contact);
        editor.commit();
        Log.d("HAR","Local file created");
    }

}