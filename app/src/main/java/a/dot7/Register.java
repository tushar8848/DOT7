package a.dot7;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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


    Button signup;
    EditText UserName,UserContact,UserPassword,UserCPassword;
    String name,contact,password,cpassword;
    String url = GlobalMethods.getURL() + "Login/CheckValidLogin";
    int validContact=0,validCpass=0,empty=0,eName=0,eContact=0,ePass=0
            ,eCpass=0;
    String StatusCode;
    a.common.MyDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getDetails();
        signup.setOnClickListener(this);
    }

    private void getDetails() {
        UserName =findViewById(R.id.Full_name);
        UserContact=findViewById(R.id.mob_no);
        UserPassword=findViewById(R.id.password);
        UserCPassword=findViewById(R.id.cnfpassword);
        signup=findViewById(R.id.btn_signup);
        focusChangeListeners();

    }
    public void callLogin(View v)
    {
        startActivity(new Intent(this,Login.class));
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


    }

    private void clearfocuses() {

        UserName.clearFocus();
        UserContact.clearFocus();
        UserCPassword.clearFocus();
        UserPassword.clearFocus();
    }

    private void setDetails()
    {
        name=UserName.getText().toString();
        contact=UserContact.getText().toString();
        password=UserPassword.getText().toString();
        cpassword=UserCPassword.getText().toString();
        if(name.length()==0)
        {
            UserName.setError("This field is required");
            eName = 0;
        }
        else eName = 1;
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
                validContact == 0 || validCpass == 0 )
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
                GlobalMethods.print(this, "Check Internet Connection");
            } else {
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
                        intent.putExtra("OTP", OTP);
                        startActivity(intent);
                    } else {
                        dialog = new MyDialog(Register.this,
                                "User Already Registered","OK");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
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




}

