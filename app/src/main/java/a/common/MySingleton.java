package a.common;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by TUSHAR on 11-03-18.
 */

public class MySingleton {

    private static MySingleton mySingleton;
    private RequestQueue requestQueue;
    private Context context;


    private MySingleton(Context context)
    {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue()
    {
        if(requestQueue==null)
        {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }
    public static synchronized MySingleton getInstance(Context context)
    {
        if(mySingleton==null)
            mySingleton = new MySingleton(context);
        return mySingleton;
    }
    public void addToRequestQueue(StringRequest request)
    {

        requestQueue.add(request);
    }
}
