package a.dot7;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import android.support.v7.widget.Toolbar;
import a.common.CheckConnection;
import a.common.GlobalMethods;
import a.common.Services;

/**
 * Created by TUSHAR on 11-03-18.
 */

public class Login extends AppCompatActivity {
    EditText userNameText;
    EditText passwordText;
    String userName;
    String password;
    EditText uname;
    EditText pwd;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {  //, @Nullable PersistableBundle persistentState



      //  Toast.makeText(this,"Login Page",Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);  //, persistentState
        setContentView(R.layout.login_page);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        uname=findViewById(R.id.user_name);
        pwd=findViewById(R.id.password);
        userName = uname.toString();
        password = pwd.toString();
    }

    public void l_signup(View view)
    {
        startActivity(new Intent(this,Register.class));
    }

    public void forgotPassword(View view){
        startActivity(new Intent(this,ForgotPassword.class));
    }
    public void userLogin(View v)
    {
        userName = userNameText.getText().toString();
        password =  passwordText.getText().toString();
        if(!CheckConnection.getInstance(this).getNetworkStatus())
        {
            GlobalMethods.print(this,"Check Internet Connection");
        }
        else {
            //internet is connected
            SharedPreferences sp = getSharedPreferences("logDetails", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("UserName", userName);
            editor.putString("Password",password);
            editor.commit();
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
}
