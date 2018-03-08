package a.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by TUSHAR on 09-03-18.
 */

public class CheckConnection {
    Context context;
    public CheckConnection(Context context)
    {
        this.context=context;
    }
    public int check()
    {
        //instantiate an object
        ConnectivityManager cm=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //get all networks information
        NetworkInfo networkInfo=cm.getActiveNetworkInfo();
        if(networkInfo!=null)
        {
            Toast.makeText(context,"Internet Connected",Toast.LENGTH_LONG).show();
            return 1;
        }
        Toast.makeText(context,"Internet Not Connected",Toast.LENGTH_LONG).show();
        return 0;
    }
}
