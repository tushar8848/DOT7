package a.common;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;

import a.dot7.Register;

/**
 * Created by Harneet Singh on 11-03-2018.
 */

public class GlobalMethods {
    static String URL = "http://192.168.43.44:3000/";

    public static String GetSubString(String res)
    {
       return res.substring(13,16);

    }
    public static void print(Context context,String msg)
    {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
    public static String getURL()
    {
        return URL;
    }
}


