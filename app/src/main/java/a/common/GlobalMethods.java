package a.common;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;

/**
 * Created by Harneet Singh on 11-03-2018.
 */

public class GlobalMethods {

    public String GetSubString(String res)
    {
       return res.substring(13,16);

    }
    public static void print(Context context,String msg)
    {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
