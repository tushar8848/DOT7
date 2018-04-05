package a.dot7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import a.common.GlobalMethods;
import a.common.MySingleton;

public class Set_New_Password extends AppCompatActivity implements View.OnClickListener {

    public static String Passing_Contact = null;

    LinearLayout UpdatePasswordButton;
    EditText PasswordText,UserCPassword;
    String Contact,Password,Cpassword;
    Intent intent;
    String StatusCode;
    ProgressBar progressBar;
    int validCpassword=0,eCpass=1,ePass=0;
    int valid=1;
    String url = GlobalMethods.getURL() + "Login/UpdatePassword";
   // String url = "http://192.168.43.161:3000/Login/UpdatePassword";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setnew_password);
        Toolbar toolbar=findViewById(R.id.tbar1);
        toolbar.setTitle("Set New Password");
        setSupportActionBar(toolbar);
        intent = getIntent();
        UpdatePasswordButton = findViewById(R.id.Update_Password);
        UserCPassword = findViewById(R.id.newconfirm1);
        PasswordText = findViewById(R.id.newp);
        Contact = intent.getStringExtra("Contact");
        progressBar = findViewById(R.id.Set_NewPass_ProgressBar);
        UpdatePasswordButton.setOnClickListener(this);
            focuschange();

    }


    private void focuschange()
    {
        UserCPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {


            }
        });
    }


    @Override
    public void onClick(View v) {
        Password=PasswordText.getText().toString();
        Cpassword=UserCPassword.getText().toString();
        if(!(Cpassword.equals(Password)))
        {
            validCpassword = 0;
            UserCPassword.setError("Passwords do not match");
        }
        else
            validCpassword = 1;
        if(Password.length() == 0)
        {
            PasswordText.setError("This field is required");
            ePass = 0;
        }
        else
            ePass = 1;
        if(Cpassword.length() == 0)
        {
            UserCPassword.setError("This field is required");
            eCpass = 0;
        }
        if(validCpassword == 1 && ePass == 1 && eCpass == 1) {
            progressBar.setVisibility(View.VISIBLE);
            callService();
        }
    }


    private void callService()
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

                    // **********stop progress bar*************************


                    if (StatusCode.contains("202")) {
                        startActivity(new Intent(Set_New_Password.this, Login.class));
                    }
                    else {
                            Log.d("HAR","NOT Verified new password");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    progressBar.setVisibility(View.GONE);
                    Log.d("HAR", volleyError.toString());
                    Log.d("HAR",StatusCode);
                    Log.d("HAR", "Error");
                    //Nitish and pooja, handle this error with a alert box or something
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("LoginID", Contact);
                    parameters.put("password", Password);
                    return parameters;
                }
            };
            MySingleton.getInstance(this).addToRequestQueue(request);
            //Log.d("HAR", "Service ab return kr ri hai");

        } catch (Exception ex) {

        }
    }


    @Override
    public void onBackPressed() {

        //Passing_Contact = Contact;
        Intent ForgotPassword = new Intent(Set_New_Password.this, ForgotPassword.class);
        ForgotPassword.putExtra("Contact",Contact);
        ForgotPassword.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK  );
        startActivity(ForgotPassword);
    }
}

