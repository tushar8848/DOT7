package a.common;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import a.dot7.ForgotPassword;
import a.dot7.R;
import a.dot7.Register;

/**
 * Created by TUSHAR on 28-03-18.
*/

public class OTP_Reader extends AppCompatActivity {
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    String OTP = null;
    Intent intent = null;
    Context context = this;
    private String Name=null, Contact=null, Password=null;
    String determine_activity=null;
    String url = "http://192.168.43.92:3000/Register";
    boolean Flag = false;
    String StatusCode;
    PinEntryEditText OTPTEXT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_generate_read);
        intent = getIntent();
        //  determine_activity = intent.getStringExtra("ForgotPassword");
        Name = intent.getStringExtra("Name");
        Contact = intent.getStringExtra("Contact");
        Password = intent.getStringExtra("Password");
        OTP = intent.getStringExtra("OTP");
        OTPTEXT = findViewById(R.id.txt_pin_entry);
        if(Name == null && Password == null && Contact != null && OTP != null)
            determine_activity = "ForgotPassword";
        else
            determine_activity = "Register";
        //if (determine_activity == null)
            initiateRegister();

    }

    private void initiateRegister() {
        Log.d("HAR", "register_work me aaya");
        read();
        Log.d("HAR", "wapas register work me aaya");
    }

    public void read()
    {
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
                    //filling OTP field
                    int otp=0;
                    otp=1000*(OTP_Received.charAt(0)-48) + 100*(OTP_Received.charAt(1)-48) +
                            10*(OTP_Received.charAt(2)-48) + (OTP_Received.charAt(3)-48);
                    OTPTEXT.setText(otp);
                    Log.d("HAR", "OTP is:"+OTP_Received);
                    if(OTP_Received.equals(OTP))
                    {
                        Log.d("HAR", "Final state success,OTP verified, otp: "+OTP_Received);
                        // If otp is required for forgot password

                        if(determine_activity.equals("ForgotPassword"))
                        {
                            Intent intent = new Intent(OTP_Reader.this,a.dot7.setnew_password.class);
                            intent.putExtra("Contact",Contact);
                            startActivity(intent);
                        }
                        Flag = true;
                        //calling webservice to register user
                        try {
                            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.
                                    Listener<String>() {

                                @Override
                                public void onResponse(String s) {
                                    StatusCode = GlobalMethods.GetSubString(s);
                                    Log.d("HAR", s);
                                    Log.d("HAR", "Satus code:" + StatusCode);
                                    //Log.d("HAR",StatusCode);
                                    if (StatusCode.contains("201")) {
                                        SharedPreferences sharedPreferences=getSharedPreferences("logDetails", OTP_Reader.this.MODE_PRIVATE);
                                        SharedPreferences.Editor editor=sharedPreferences.edit();
                                        editor.putString("Name",Name);
                                        editor.putString("Password",Password);
                                        editor.putString("UserName",Contact);
                                        editor.commit();

                                        Log.d("HAR", "Local file created");
                                        //  Toast.makeText(Services.this, "User Registered succesfully", Toast.LENGTH_SHORT).show();
                                        // **********stop progress bar*************************
                                        startActivity(new Intent(OTP_Reader.this, TempActivity.class));
                                    } else if (StatusCode.contains("302")) {
                                        GlobalMethods.print(context, "Data Found");
                                        //  StatusFlag=1;
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
                                    parameters.put("LoginID", Contact);
                                    parameters.put("password", Password);
                                    if (Name != null)
                                        parameters.put("Name", Name);
                                    return parameters;
                                }
                            };
                            MySingleton.getInstance(context).addToRequestQueue(request);
                        }
                        catch (Exception ex)
                        {
                            //return false;
                        }
                    }
                    else
                    {
                            //Here OTP entered is wrong, handle the case
                    }
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
}