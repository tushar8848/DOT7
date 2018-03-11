package a.common;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;
import a.common.GlobalMethods;

/**
 * Created by TUSHAR and Harneet on 11-03-18.
 */

public class ValidateUserCredentials {

    private GlobalMethods Obj = new GlobalMethods();
    static ValidateUserCredentials validateUserCredentials;
    private Context context;
    private String StatusCode = null;
    ValidateUserCredentials(Context context)
    {
        this.context = context;
    }

    public boolean validateCredentials(final String UserName, final String Password)
    {
        try {
            String loginUrl = "http://192.168.43.62:3000/Login";
            StringRequest request = new StringRequest(Request.Method.POST, loginUrl, new Response.
                    Listener<String>() {

                @Override
                public void onResponse(String s) {
                    StatusCode = Obj.GetSubString(s);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    //Nitish and pooja, handle this error with a alert box or something
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("LoginID", "9039216432");
                    parameters.put("password", "hello");
                    return parameters;
                }
            };

            RequestQueue rQueue = Volley.newRequestQueue(context);
            rQueue.add(request);

            if (StatusCode.contains("302")) {
                Toast.makeText(context,"Data Found",Toast.LENGTH_LONG).show();
                return true;
            }
            else
            {
                Toast.makeText(context,"No Data Found",Toast.LENGTH_LONG).show();
                return false;
            }

        }
        catch (Exception ex)
        {
            return false;
        }
    }

    public static ValidateUserCredentials getInstance(Context context)
    {
        if(validateUserCredentials == null) {
            validateUserCredentials = new ValidateUserCredentials(context);
        }
        return validateUserCredentials;
    }
}


        //********************OLD SERVICE CALL CODE*******************************//

/*String loginUrl = "http://192.168.43.106:3000/Login";      //Harneet Fill this url for login details
        final int[] result = new int[1];
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, loginUrl,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("HAR",response.toString());
                    Toast.makeText(context,"Internet Not Connected",Toast.LENGTH_LONG).show();
                   //result[0] = response.getInt("status");    //Harneet mark this key
                    String chk = response;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Connected",Toast.LENGTH_LONG).show();
                Log.d("HAR",error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data = new HashMap<>();
                data.put("LoginID","9039216432");
                data.put("password","hello");
                return data;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json");
                return params;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
        /*if(result[0]==302)
        return true;
        else
            return false;*/