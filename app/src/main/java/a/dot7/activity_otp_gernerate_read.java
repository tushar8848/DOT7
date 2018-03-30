package a.dot7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class activity_otp_gernerate_read extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_generate_read);
    }


    public void new_password(View view)
    {
        startActivity(new Intent(this,setnew_password.class));
    }



}
