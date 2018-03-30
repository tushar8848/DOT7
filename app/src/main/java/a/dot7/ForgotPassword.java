package a.dot7;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import a.common.GlobalMethods;
import a.common.MyDialog;
import a.common.MySingleton;
import a.common.OTP_Generator;
import a.common.OTP_Reader;
import a.common.TempActivity;

public class ForgotPassword extends Activity implements View.OnClickListener{

    AppCompatButton forgot_password;
    EditText ContactText;
    String Contact;
    String StatusCode;
    String url = "http://192.168.43.184:3000/Login/CheckValidLogin";
    MyDialog dialog;
    private AlertDialog CustomDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ContactText = findViewById(R.id.mob_number);
        forgot_password = findViewById(R.id.forgot_password_button);
        forgot_password.setOnClickListener(this);
    }



    public void onClick(View view)
    {
        //startActivity(new Intent(this,activity_otp_gernerate_read.class));
        Contact = ContactText.getText().toString();
        if(Contact!=null)
            callService();
        else {
            dialog = new MyDialog(this,"Invalid Mobile number","OK");
        }
    }
    private void callService()
    {
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

                        //startActivity(new Intent(ForgotPassword.this, TempActivity.class));
                        //StatusFlag=1;
                        String OTP = OTP_Generator.getInstance(ForgotPassword.this).Generate();
                        //sending sms
                        boolean flag = OTP_Generator.getInstance(ForgotPassword.this).sendMessage(OTP,Contact);
                        Intent intent = new Intent(ForgotPassword.this,OTP_Reader.class);
                        intent.putExtra("Name",(String) null);
                        intent.putExtra("Password",(String) null);
                        intent.putExtra("Contact",Contact);
                        intent.putExtra("OTP",OTP);
                        startActivity(intent);





                    } else {

                        AlertDialog.Builder builder=new AlertDialog.Builder(ForgotPassword.this);
                        builder.setMessage("User Not Registered");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                startActivity(new Intent(ForgotPassword.this,Register.class));
                            }
                        });
                        CustomDialog=builder.create();
                        CustomDialog.show();



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

                    // if(Name != null)
                    //   parameters.put("Name",Name);
                    return parameters;
                }
            };
            MySingleton.getInstance(this).addToRequestQueue(request);
            Log.d("HAR", "Service ab return kr ri hai");

        } catch (Exception ex) {

        }
    }

}
