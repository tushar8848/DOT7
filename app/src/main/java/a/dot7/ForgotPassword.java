package a.dot7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.widget.Button;

public class ForgotPassword extends Activity {

    AppCompatButton forgot_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        onclick();
    }

    private void onclick() {
        Intent intent = new Intent(this,Otp_generate_read.class);
        intent.putExtra("ForgotPassword","forgotpassword");
        startActivity(intent);
    }
}
