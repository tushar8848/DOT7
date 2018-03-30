package a.dot7;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import a.common.CheckConnection;
import a.common.GlobalMethods;
import a.common.MyDialog;
import a.common.OTP_Generator;
import a.common.OTP_Reader;

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
        UserName =findViewById(R.id.Full_name);
        UserContact=findViewById(R.id.mob_no);
        UserPassword=findViewById(R.id.password);
        signup=findViewById(R.id.btn_signup);

    }
    private void setDetails()
    {
        name=UserName.getText().toString();
        contact=UserContact.getText().toString();
        password=UserPassword.getText().toString();
    }
    public void s_login(View view)
    {
        startActivity(new Intent(this,Login.class));
    }

    public void goto_genotp(View view)
    {
        startActivity(new Intent(this,activity_otp_gernerate_read.class));
    }

    @Override
    public void onClick(View view) {


        setDetails();
        if (!CheckConnection.getInstance(this).getNetworkStatus())
        {
            GlobalMethods.print(this, "Check Internet Connection");
        }
        else
        {
            Log.e("pass",password);
            if (name != null && password != null && contact != null)
            {
                boolean flag = false;
                //generating OTP
               String OTP = OTP_Generator.getInstance(this).Generate();
               //sending sms
               flag = OTP_Generator.getInstance(this).sendMessage(OTP,contact);
               if(flag == true)
               Log.d("HAR","OTP generated and sent succesfully "+OTP);
               else
                   Log.d("HAR","OTP generated but not sent, contact:"+contact);


                Intent intent = new Intent(this,OTP_Reader.class);
                intent.putExtra("Name",name);
                intent.putExtra("Password",password);
                intent.putExtra("Contact",contact);
                intent.putExtra("OTP",OTP);
                startActivity(intent);
            }
            else
            {
                GlobalMethods.print(this,"All feilds are required");
                //pooja and nitish either make a hidden textbox for it
            }
        }
    }




}

