package a.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by TUSHAR on 09-03-18.
 */

public class CheckConnection {
    private Context context;
    private static CheckConnection checkConnection=null;
    private CheckConnection(Context context)
    {
        this.context=context;
    }

    public boolean getNetworkStatus()
    {
        //instantiate an object
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.
                CONNECTIVITY_SERVICE);

        //get all networks information
        NetworkInfo networkInfo = null;
        try
        {
            networkInfo = manager.getActiveNetworkInfo();  //getActiveNetworkInfo may throw NullPointerException
        }

        catch (NullPointerException e)
        {
            GlobalMethods.print(context,e.toString());
        }
        if(networkInfo != null)
        {
            //GlobalMethods.print(context,"Internet Connected");
            return true;
        }
       // GlobalMethods.print(context,"Internet Not Connected");
        return false;
    }
    public static CheckConnection getInstance(Context context)
    {
        if(checkConnection==null)
            checkConnection = new CheckConnection(context);
        return checkConnection;
    }
}
