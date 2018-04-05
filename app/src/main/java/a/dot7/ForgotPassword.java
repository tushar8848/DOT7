package a.dot7;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

    LinearLayout forgot_password;
    EditText ContactText;
    String Contact;
    String StatusCode;
    String url = GlobalMethods.getURL() + "Login/CheckValidLogin";
    //String url = "http://192.168.43.161:3000/Login/CheckValidLogin";
    MyDialog dialog;
    ProgressBar progressBar;
    int validContact = 0;
    private AlertDialog CustomDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ContactText = findViewById(R.id.Mob_Number);
        forgot_password = findViewById(R.id.Forgot_Password_Button);
        forgot_password.setOnClickListener(this);
        progressBar = findViewById(R.id.Forgot_Password_Progress_bar);
        focusChange();
    }
    private void focusChange()
    {
        ContactText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    Contact = ContactText.getText().toString();
                    if(!(Contact.length()==10))
                    {
                        ContactText.setError("Invalid Contact");
                        validContact = 0;
                    }
                    else
                        validContact = 1;
                }
            }
        });
    }


    public void onClick(View view)
    {
        //startActivity(new Intent(this,Otp.class));
        Contact = ContactText.getText().toString();
        if(Contact.length()==0)
        {
            ContactText.setError("This field is required");
        }
        else if(Contact.length()!=0 && validContact == 1) {
            progressBar.setVisibility(View.VISIBLE);
            callService(view);
        }
    }
    private void callService(final View view)
    {
        try {
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.
                    Listener<String>() {

                @Override
                public void onResponse(String s) {
                    progressBar.setVisibility(View.GONE);
                    StatusCode = GlobalMethods.GetSubString(s);
                    Log.d("HAR", s);
                    Log.d("HAR", "Satus code:" + StatusCode);
                    //Log.d("HAR",StatusCode);

                    // *************************************************stop progress bar*************************


                    if (StatusCode.contains("302")) {
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
                    progressBar.setVisibility(View.GONE);
                    Log.d("HAR", volleyError.toString());
                    Log.d("HAR", "Error");
                    Snackbar.make(view, "Some Error Occured",
                            Snackbar.LENGTH_LONG)
                            .setAction("Retry", null).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("LoginID", Contact);
                    return parameters;
                }
            };
            MySingleton.getInstance(this).addToRequestQueue(request);
            Log.d("HAR", "Service ab return kr ri hai");

        } catch (Exception ex) {

        }
    }

}
