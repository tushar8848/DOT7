package a.dot7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.widget.Button;
import android.view.View;

public class ForgotPassword extends Activity {

    AppCompatButton forgot_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }



    public void onClick(View view)
    {
        startActivity(new Intent(this,Otp_generate_read.class));
    }


}
