package a.dot7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class setnew_password extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setnew_password);
    }


    public void goto_login(View view)
    {
        startActivity(new Intent(this,Login.class));
    }
}
