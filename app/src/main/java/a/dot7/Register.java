package a.dot7;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Map;
import java.util.Set;

import a.common.CheckConnection;
import a.common.GlobalMethods;
import a.common.UserCredentials;

public class Register extends AppCompatActivity implements View.OnClickListener {


    Button signup;
    EditText Username,Useremail,Userpassword;
    String name,email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDetails();
        signup.setOnClickListener(this);
    }

    private void getDetails() {
        /*Username=findViewById();          // pooja and nitish fill here the ids.
        Useremail=findViewById();
        Userpassword=findViewById();
        signup=findViewById();*/
        name=Username.getText().toString();
        email=Useremail.getText().toString();
        password=Userpassword.getText().toString();
    }

    @Override
    public void onClick(View view) {
        if (!CheckConnection.getInstance(this).getNetworkStatus())
        {
            GlobalMethods.print(this, "Check Internet Connection");
        }
        else
        {
            boolean Status = UserCredentials.getInstance(this).Register(name, password, email);

            if (Status) {
                GlobalMethods.print(this, "SuccessFully Registered!");
                createLocalfile(name, password);
                /*Intent intent=new Intent(this,RestaurantView.class);
                startActivity(intent);*/
            }
            else {
                GlobalMethods.print(this, "Something went wrong! Try Again");
                MyAlert mydialog=new MyAlert(this);
                mydialog.show(getFragmentManager(),"mydialog");
            }
        }
    }

    private void createLocalfile(String name, String password) {
        SharedPreferences sharedPreferences=getSharedPreferences("logDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("UserName",name);
        editor.putString("Password",password);
        editor.commit();
    }

    private class MyAlert extends DialogFragment{

        Context context;
        public MyAlert(Register register) {
            this.context=register;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(context);
            builder.setMessage("Something went wrong! Try Again");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Useremail.setText("");
                    Username.setText("");
                    Userpassword.setText("");
                }
            });
            Dialog dialog=builder.create();
            return dialog;
        }
    }

}

