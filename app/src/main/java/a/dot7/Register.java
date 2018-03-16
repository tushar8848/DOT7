package a.dot7;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import a.common.CheckConnection;
import a.common.GlobalMethods;
import a.common.MyDialog;
import a.common.Services;



public class Register extends AppCompatActivity implements View.OnClickListener {


    Button signup;
    EditText UserName,UserContact,UserPassword;
    String name,contact,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getDetails();
        signup.setOnClickListener(this);
    }

    private void getDetails() {
        /*Username=findViewById();          // pooja and nitish fill here the ids.
        Usercontact=findViewById();
        Userpassword=findViewById();
        signup=findViewById();*/
        name=UserName.getText().toString();
        contact=UserContact.getText().toString();
        password=UserPassword.getText().toString();
    }
    public void s_login(View view)
    {
        startActivity(new Intent(this,Login.class));
    }

    @Override
    public void onClick(View view) {
        if (!CheckConnection.getInstance(this).getNetworkStatus())
        {
            GlobalMethods.print(this, "Check Internet Connection");
        }
        else
        {
            boolean Status = Services.getInstance(this).Register(name, password, contact);

            if (Status) {
                createLocalfile(name, password,contact);
                /*Intent intent=new Intent(this,RestaurantView.class);
                startActivity(intent);*/
            }
            else {
                //GlobalMethods.print(this, "Something went wrong! Try Again");
                MyDialog myDialog=new MyDialog(this,"Something went wrong! Try Again","OK");
                UserContact.setText("");
                UserName.setText("");
                UserPassword.setText("");
            }
        }
    }

    private void createLocalfile(String name, String password, String contact) {
        SharedPreferences sharedPreferences=getSharedPreferences("logDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("UserName",name);
        editor.putString("Password",password);
        editor.putString("Contact",contact);
        editor.commit();
    }

}

