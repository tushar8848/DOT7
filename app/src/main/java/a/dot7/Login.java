package a.dot7;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import a.common.CheckConnection;
import a.common.ValidateUserCredentials;

/**
 * Created by TUSHAR on 11-03-18.
 */

public class Login extends Activity {
    EditText userNameText;
    EditText passwordText;
    String userName;
    String password;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        //setContentView();
        //userName = findViewById();
        //password = findViewById();
    }

    public void userLogin(View v)
    {
        userName = userNameText.getText().toString();
        password =  passwordText.getText().toString();
        if(!CheckConnection.getInstance(this).getNetworkStatus())
        {
            Toast.makeText(this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
        else {
            //internet is connected
            SharedPreferences sp = getSharedPreferences("logDetails", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("UserName", userName);
            editor.putString("Password",password);
            editor.commit();
            if(ValidateUserCredentials.getInstance(this).Validate(userName,password))
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
