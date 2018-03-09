package a.dot7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import a.common.*; //always import other packages here and not use it directly in code

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getNetworkState();

    }

    private void getNetworkState()
    {
        CheckConnection connection = new CheckConnection(this);
        Boolean State = connection.getNetworkStatus();
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
