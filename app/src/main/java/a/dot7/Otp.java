package a.dot7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Otp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_read);
    }


    public void new_password(View view)
    {
        startActivity(new Intent(this,Set_New_Password.class));
    }



}
