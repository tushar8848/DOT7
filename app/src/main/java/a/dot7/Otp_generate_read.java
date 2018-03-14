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

import android.content.BroadcastReceiver;
import android.widget.TextView;

public class Otp_generate_read extends AppCompatActivity {

    private int status=0;
    private String url,msg;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_generate_read);
        boolean code=generate();
        if (code)
        {
            read();
        }
        else
        {
            GlobalMethods.print(this,"Something went wrong! Try Again!");
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        TextView tv = (TextView) findViewById(R.id.txtview);
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                String message = intent.getStringExtra("message");
                //message=message.substring()           specify index of code in the message
                if(message==msg)
                {
                    // intent to restaurant view
                }
            }
            else
            {
                tv.setText("Error Validating Phone No. \n Resend Code!");
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

    private boolean generate() {
        Random generate=new Random();
        int otp=0;
        for (int i=0;i<4;i++)
        {
            int x=generate.nextInt(10);
            otp=otp*10+x;
        }

        url="http://172.31.143.55:3000/";    //harneet write srvice name here
        msg= String.valueOf(otp);
        boolean code=servicecall(msg,url);
        return code;

    }

    private boolean servicecall(final String msg, String url) {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String code= GlobalMethods.GetSubString(response);
                if (code.contains("302")) {
                    status=1;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyDialog myDialog=new MyDialog(Otp_generate_read.this,error.toString());
                status=0;
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<>();
                param.put("otp",msg);
                return param;
            }
        };
        if (status==1)
        {
            return true;
        }
        else
            return false;
    }

    private void read() {

        if (checkpermissions())
        {

        }
        else
        {
            MyDialog myDialog=new MyDialog(this,"Cannot access OTP without permissions.\nKindly grant Permissions!");
            checkpermissions();
        }
    }


    private boolean checkpermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);
        int receiveSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        int readSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_MMS);
        }

        if (readSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
}
