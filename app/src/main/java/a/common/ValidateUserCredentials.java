package a.common;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TUSHAR on 11-03-18.
 */

public class ValidateUserCredentials {
    static ValidateUserCredentials validateUserCredentials;
    Context context;
    ValidateUserCredentials(Context context)
    {
        this.context = context;
    }

    public boolean validateCredentials(final String userName, final String password)
    {
        String loginUrl = null;      //Harneet Fill this url for login details
        final boolean[] result = new boolean[1];
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, loginUrl,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    result[0] = response.getBoolean("status");    //Harneet mark this key
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data = new HashMap<>();
                data.put("UserName",userName);
                data.put("Password",password);
                return super.getParams();
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
        return result[0];
    }
    public static ValidateUserCredentials getInstance(Context context)
    {
        if(validateUserCredentials==null)
            validateUserCredentials = new ValidateUserCredentials(context);
        return validateUserCredentials;
    }
}
