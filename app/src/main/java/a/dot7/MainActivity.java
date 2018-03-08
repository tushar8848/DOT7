package a.dot7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        a.common.CheckConnection cc=new a.common.CheckConnection(this);
        int connection=cc.check();
    }

    private void TestFunction()
    {
        int i=0;
    }
   /*
   private void startactivity(View v)
    {
        Intent i=new Intent(this, a.services.CheckConnection.class);
        if(v.getId()==R.id.Check_Connection)
            startActivity(i);

    }
    */

}
