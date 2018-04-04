package a.dot7;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.support.v7.widget.Toolbar;
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
import a.common.MySingleton;
import a.common.TempActivity;
import a.common.MyDialog;
import a.home_screen.Restaurant_Recycler_View;

/**
 * Created by TUSHAR on 11-03-18.
 */

public class Login extends AppCompatActivity implements View.OnClickListener{
    EditText UserNameText;
    EditText PasswordText;
    String UserName;
    String Password;
    LinearLayout LoginButton;
    String StatusCode;
    ProgressBar progressBar;
    int validContact=0,ePass=0,eContact;
    String url = GlobalMethods.getURL() + "Login";
    //String url = "http://192.168.43.161:3000/Login";
    MyDialog dialog;
    private AlertDialog CustomDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {  //, @Nullable PersistableBundle persistentState
        super.onCreate(savedInstanceState);  //, persistentState
        setContentView(R.layout.login_page);
        Toolbar toolbar=findViewById(R.id.Tbar1);
        toolbar.setTitle("Login");
        setSupportActionBar(toolbar);
        progressBar = findViewById(R.id.Login_Progress_Bar);
        setDetails();
        LoginButton.setOnClickListener(this);
        focuschange();
    }
    private void focuschange()
    {
        UserNameText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    UserName=UserNameText.getText().toString();
                    if(!(UserName.length()==10))
                    {
                        UserNameText.setError("Invalid Contact");
                        validContact = 0;
                    }
                    else
                        validContact = 1;

                }

            }
        });
    }
    private void setDetails()
    {
        UserNameText = findViewById(R.id.Mob_No);
        PasswordText = findViewById(R.id.Password);
        LoginButton = findViewById(R.id.Input_Layout_Name_Login_Button);
    }
    private void getDetails()
    {
        UserName = UserNameText.getText().toString();
        Password = PasswordText.getText().toString();
        if(UserName.length()==0)
        {
            UserNameText.setError("This field is required");
            eContact = 0;
        }
        else eContact = 1;
        if(Password.length()==0)
        {
            PasswordText.setError("This field is required");
            ePass = 0;
        }
        else ePass = 1;
    }

    public void Call_Signup(View view)
    {
        startActivity(new Intent(this,Register.class));
    }

    public void forgotPassword(View view){
        startActivity(new Intent(this,ForgotPassword.class));
    }

    @Override
    public void onClick(View v) {
        getDetails();
        if(eContact == 1 && ePass == 1 && validContact == 1)
        {
             if(!CheckConnection.getInstance(this).getNetworkStatus())
             {
                GlobalMethods.print(this,"Check Internet Connection");
               }
             else {
                 //internet is connected
                 Log.d("HAR", "Username:" + UserName + " Password:" + Password);
                 progressBar.setVisibility(View.VISIBLE);
                     callService(v);
             }
        }
    }
    public void callService(final View view)
    {
        try {
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.
                    Listener<String>() {

                @Override
                public void onResponse(String s) {
                    progressBar.setVisibility(View.GONE);
                    StatusCode = GlobalMethods.GetSubString(s);
                    Log.d("HAR", s);



                    // **********stop progress bar*************************


                    if (StatusCode.contains("302")) {
                        SharedPreferences sp =
                                getSharedPreferences("logDetails", a.dot7.Login.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("UserName", UserName);
                        editor.putString("Password",Password);
                        editor.commit();
                        startActivity(new Intent(Login.this, Restaurant_Recycler_View.class));
                    }
                    else if(StatusCode.contains("404"))
                    {
                        dialog = new MyDialog(Login.this,
                                "Mobile number not registered","OK");
                    }
                    else {
                        Snackbar.make(view, "Incorrect Password",
                                Snackbar.LENGTH_LONG)
                                .setAction("OK", null).show();
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
                    parameters.put("LoginID", UserName);
                    parameters.put("password", Password);
                    // if(Name != null)
                    //   parameters.put("Name",Name);
                    return parameters;
                }
            };
            MySingleton.getInstance(this).addToRequestQueue(request);


        } catch (Exception ex) {

        }
    }
}