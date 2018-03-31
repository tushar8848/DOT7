package a.dot7;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.support.v7.widget.Toolbar;

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

/**
 * Created by TUSHAR on 11-03-18.
 */

public class Login extends AppCompatActivity implements View.OnClickListener{
    EditText UserNameText;
    EditText PasswordText;
    String UserName;
    String Password;
    Button LoginButton;
    String StatusCode;
    String url = "http://192.168.43.161:3000/Login";
    MyDialog dialog;
    private AlertDialog CustomDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {  //, @Nullable PersistableBundle persistentState




        super.onCreate(savedInstanceState);  //, persistentState
        setContentView(R.layout.login_page);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setDetails();
        LoginButton.setOnClickListener(this);

    }
    private void setDetails()
    {
        UserNameText = findViewById(R.id.mob_no);
        PasswordText = findViewById(R.id.password);
        LoginButton = findViewById(R.id.input_layout_name3);
    }
    private void getDetails()
    {
        UserName = UserNameText.getText().toString();
        Password = PasswordText.getText().toString();
    }

    public void Call_Signup(View view)
    {
        startActivity(new Intent(this,Register.class));
    }
    public void res_view_page(View view)
    {
        startActivity(new Intent(this,Restaurant_Recycler_View.class));
    }

    public void forgotPassword(View view){
        startActivity(new Intent(this,ForgotPassword.class));
    }

    @Override
    public void onClick(View v) {
        getDetails();
        if(!CheckConnection.getInstance(this).getNetworkStatus())
        {
            GlobalMethods.print(this,"Check Internet Connection");
        }
        else {
            //internet is connected
            Log.d("HAR","Username:"+UserName+" Password:"+Password);
            if(UserName != null && Password != null) {
                    callService();

            }
            else {
                //************************Review This***************************************
                dialog = new MyDialog(this,"Invalid Input!!!","OK");
            }
        }
    }
    public void callService()
    {
        try {
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.
                    Listener<String>() {

                @Override
                public void onResponse(String s) {
                    StatusCode = GlobalMethods.GetSubString(s);
                    Log.d("HAR", s);



                    // **********stop progress bar*************************


                    if (StatusCode.contains("302")) {
                        SharedPreferences sp = getSharedPreferences("logDetails", a.dot7.Login.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("UserName", UserName);
                        editor.putString("Password",Password);
                        editor.commit();
                        startActivity(new Intent(Login.this, TempActivity.class));
                    } else {

                        AlertDialog.Builder builder=new AlertDialog.Builder(Login.this);
                        builder.setMessage("Invalid Credentials!!!");
                        builder.setPositiveButton("Login Again", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                startActivity(new Intent(Login.this,Login.class));
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



  /* public void userLogin(View v)
    {

        if(!CheckConnection.getInstance(this).getNetworkStatus())
        {
            GlobalMethods.print(this,"Check Internet Connection");
        }
        else {
            //internet is connected
            SharedPreferences sp = getSharedPreferences("logDetails", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("UserName", UserName);
            editor.putString("Password",Password);
            editor.commit();
            /*
            if(Services.getInstance(this).Validate("9039216432","hello"))
            {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Incorrect userinfo", Toast.LENGTH_SHORT).show();
            }

        }
    }
*/