package a.dot7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import a.common.CheckConnection;
import a.common.GlobalMethods;
import a.common.OTP_Generator;
import a.common.OTP_Reader;

public class Register extends AppCompatActivity implements View.OnClickListener {


    Button signup;
    EditText UserName,UserContact,UserPassword,UserCPassword;
    String name,contact,password,cpassword;
    int valid = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getDetails();

    }

    private void getDetails() {
        UserName =findViewById(R.id.Full_name);
        UserContact=findViewById(R.id.mob_no);
        UserPassword=findViewById(R.id.password);
        UserCPassword=findViewById(R.id.cnfpassword);
        signup=findViewById(R.id.btn_signup);
        signup.setOnClickListener(this);
        focusChangeListeners();


    }
    private void focusChangeListeners()
    {
        UserCPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //Log.d("HAR","Focus is changes "+hasFocus);
                //if(hasFocus)
                //{
                password=UserPassword.getText().toString();
                cpassword=UserCPassword.getText().toString();
                if(!cpassword.equals(password))
                {
                    UserCPassword.setError("Passwords do not match");
                    valid = 0;
                }
                else {
                    valid = 1;
                    signup.setOnClickListener(Register.this);
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
                        valid = 0;
                    }
                    else
                        valid = 1;

                }
            }
        });
    }

    private void setDetails()
    {
        name=UserName.getText().toString();
        contact=UserContact.getText().toString();
        password=UserPassword.getText().toString();
        cpassword=UserCPassword.getText().toString();

    }

    public void s_login(View view)
    {
        startActivity(new Intent(this,Login.class));
    }
    

    @Override
    public void onClick(View view) {


        setDetails();
        if(name == null || password == null || cpassword == null)
            valid = 0;
        if(valid == 1) {

            if (!CheckConnection.getInstance(this).getNetworkStatus()) {
                GlobalMethods.print(this, "Check Internet Connection");
            } else {
                Log.e("pass", password);
                if (name != null && password != null && contact != null) {
                    boolean flag = false;
                    //generating OTP
                    String OTP = OTP_Generator.getInstance(this).Generate();
                    //sending sms
                    flag = OTP_Generator.getInstance(this).sendMessage(OTP, contact);
                    if (flag == true)
                        Log.d("HAR", "OTP generated and sent succesfully " + OTP);
                    else
                        Log.d("HAR", "OTP generated but not sent, contact:" + contact);


                    Intent intent = new Intent(this, OTP_Reader.class);
                    intent.putExtra("Name", name);
                    intent.putExtra("Password", password);
                    intent.putExtra("Contact", contact);
                    intent.putExtra("OTP", OTP);
                    startActivity(intent);
                } else {
                    GlobalMethods.print(this, "All feilds are required");
                    //pooja and nitish either make a hidden textbox for it
                }
            }
        }
    }




}

