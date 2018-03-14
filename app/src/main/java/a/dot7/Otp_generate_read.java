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
import android.widget.TextView;


//Create button get otp type something and

public class Otp_generate_read extends AppCompatActivity {

    private int Status=0;
    private final String URL = "http://172.31.143.78:3000/OTP";
    private String Message;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_generate_read);
        boolean code=Generate();
        if (code)
        {
            read();
        }
        else
        {
            GlobalMethods.print(this,"Something went wrong! Try Again!");
            // Pooja and Nitish Change to alert if ypu want
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        TextView otp_message = (TextView) findViewById(R.id.txtview);
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equalsIgnoreCase("otp")) {
                String message = intent.getStringExtra("message");
                //message=message.substring()           specify index of code in the message
                if(message.equals(Message))
                {
                    // intent to restaurant view
                }
            }
            else
            {
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

    private boolean Generate() {
        Random generate=new Random();
        char[] otp=new char[4];
        for (int i=0;i<4;i++)
        {
            int x=generate.nextInt(10);
            otp[i]=(char)(x+'0');
        }
        String msg= String.valueOf(otp);
        boolean code = Services.getInstance(this).otp_service_call(Message, URL,this);
        return code;

    }

    private void read() {

        if (Permissions.getInstance(this).checkpermissions(this,REQUEST_ID_MULTIPLE_PERMISSIONS))
        {

        }
        else
        {
            MyDialog myDialog=new MyDialog(this,"Cannot access OTP without permissions!","Grant Permissions");
            Permissions.getInstance(this).checkpermissions(this,REQUEST_ID_MULTIPLE_PERMISSIONS);
        }
    }

}
