package a.dot7;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import a.common.CheckConnection;
import a.common.GlobalMethods;
import a.common.MyDialog;
import a.common.MySingleton;
import a.common.OTP_Generator;
import a.common.OTP_Reader;

public class Register extends AppCompatActivity implements View.OnClickListener {


    LinearLayout signup;
    EditText UserName,UserContact,UserPassword,UserCPassword, UserEmail;
    String name,contact,password,cpassword,Email;
    String url = GlobalMethods.getURL() + "Login/CheckValidLogin";
    int validContact=0,validCpass=0,empty=0,eName=0,eContact=0,ePass=0
            ,eCpass=0, eEmail=0, validMail=0;
    String StatusCode;
    ProgressBar progressBar;

    a.common.MyDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar=findViewById(R.id.Tbar1);
        toolbar.setTitle("Register");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.Register_Progress_Bar);
        progressBar.setIndeterminate(true);
        getDetails();
        signup.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return true;
        }
    }

    private void getDetails() {
        UserName =findViewById(R.id.Full_Name);
        UserContact=findViewById(R.id.Mob_No);
        UserPassword=findViewById(R.id.Password);
        UserCPassword=findViewById(R.id.Cnfpassword);
        UserEmail = findViewById(R.id.Email_Id);
        signup=findViewById(R.id.Btn_Signup);
        focusChangeListeners();

    }
    public void callLogin(View view)
    {
        Intent intent=new Intent(this,Login.class);
        startActivity(intent);
    }


    private void focusChangeListeners()
    {
        UserCPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //Log.d("HAR","Focus is changes "+hasFocus);
                //if(hasFocus)
                password=UserPassword.getText().toString();
                cpassword=UserCPassword.getText().toString();
                // Log.d("HAR","Focus changed to "+hasFocus+" Password:"+password+" cpass:"+cpassword );
                if(!cpassword.equals(password))
                {
                    UserCPassword.setError("Passwords do not match");
                    validCpass = 0;
                }
                else {
                    validCpass = 1;

                }
                //}
            }
        });
        UserContact.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    contact=UserContact.getText().toString();
                    if(!(contact.length()==10))
                    {
                        UserContact.setError("Invalid Contact");
                        validContact = 0;
                    }
                    else
                        validContact = 1;

                }
            }
        });
        UserEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    Email = UserEmail.getText().toString();
                    if(!Email.endsWith(".com")||!Email.contains("@"))
                    {
                        UserEmail.setError("Invalid Email Id");
                        validMail = 0;
                    }
                    else
                        validMail = 1;
                }
            }
        });

    }

    private void clearfocuses() {

        UserName.clearFocus();
        UserContact.clearFocus();
        UserEmail.clearFocus();
        UserCPassword.clearFocus();
        UserPassword.clearFocus();
    }

    private void setDetails()
    {
        name=UserName.getText().toString();
        contact=UserContact.getText().toString();
        password=UserPassword.getText().toString();
        cpassword=UserCPassword.getText().toString();
        Email = UserEmail.getText().toString();

        if(name.length()==0)
        {
            UserName.setError("This field is required");
            eName = 0;
        }
        else eName = 1;
        if(Email.length()==0)
        {
            UserEmail.setError("This field is required");
            eEmail = 0;
        }
        else eEmail = 1;
        if(password.length()==0)
        {
            UserPassword.setError("This field is required");
            ePass = 0;
        }
        else if(password.length()<6)
        {
            UserPassword.setError("Minimum 6 characters required");
            ePass = 0;
        }
        else ePass = 1;
        if(contact.length()==0)
        {
            UserContact.setError("This field is required");
            eContact = 0;
        }
        else eContact = 1;
        if(cpassword.length()==0)
        {
            UserCPassword.setError("This field is required");
            eCpass = 0;
        }
        else if(!cpassword.equals(password))
        {
            UserCPassword.setError("Passwords do not match");
            eCpass = 0;
        }
        else eCpass = 1;
        if(eName == 0 || eContact == 0 || ePass == 0 || eCpass == 0 ||
                validContact == 0 || validCpass == 0 || eEmail == 0 || validMail == 0)
        {
            empty = 1;
        }
        else
            empty = 0;

    }
    @Override
    public void onClick(View view) {

        clearfocuses();
        setDetails();

        if(empty == 0) {

            if (!CheckConnection.getInstance(this).getNetworkStatus()) {
                Snackbar.make(view, "Some Error Occured",
                        Snackbar.LENGTH_LONG)
                        .setAction("Retry", null).show();
            } else {
                    //internet is connected
                    progressBar.setVisibility(View.VISIBLE);
                    callService(view);
            }
        }
    }
    private void callService(final View view)
    {
        try {
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.
                    Listener<String>() {

                @Override
                public void onResponse(String s) {
                    // progressBar.setActivated(false);
                    progressBar.setVisibility(View.GONE);
                    StatusCode = GlobalMethods.GetSubString(s);
                    Log.d("HAR", s);
                    // ********************************************************stop progress bar*************************

                    if (!StatusCode.contains("302")) {

                        boolean flag;
                        //generating OTP
                        String OTP = OTP_Generator.getInstance(Register.this).Generate();
                        //sending sms
                        flag = OTP_Generator.getInstance(Register.this).sendMessage(OTP, contact);
                        if (flag)
                            Log.d("HAR", "OTP generated and sent succesfully " + OTP);
                        else
                            Log.d("HAR", "OTP generated but not sent, contact:" + contact);


                        Intent intent = new Intent(Register.this, OTP_Reader.class);
                        intent.putExtra("Name", name);
                        intent.putExtra("Password", password);
                        intent.putExtra("Contact", contact);
                        intent.putExtra("Email", Email);
                        intent.putExtra("OTP", OTP);
                        startActivity(intent);
                    } else {
                        Snackbar.make(view, "User Already Registered",
                                Snackbar.LENGTH_LONG)
                                .setAction("OK", null).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    // setProgressBarIndeterminate(false);
                    progressBar.setVisibility(View.GONE);
                    //progressBar.setActivated(false);
                    Log.d("HAR", volleyError.toString());
                    Log.d("HAR", "Error");
                    //***************************************Stop Progress Bar********************************
                    Snackbar.make(view, "Some Error Occured",
                            Snackbar.LENGTH_LONG)
                            .setAction("Retry", null).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("LoginID", contact);

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

    @Override
    public void onBackPressed() {

        Intent ForgotPassword = new Intent(Register.this, ScreenSlideActivity.class);
        ForgotPassword.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP   );
        startActivity(ForgotPassword);
    }


}
