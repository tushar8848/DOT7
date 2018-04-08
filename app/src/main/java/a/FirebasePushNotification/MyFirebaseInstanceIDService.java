package a.FirebasePushNotification;



import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;
import java.util.Map;

import a.common.GlobalMethods;
import a.common.MySingleton;


//Class extending FirebaseInstanceIdService
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    public String refreshedToken = null;

    @Override
    public void onTokenRefresh() {

        //Getting registration token
         refreshedToken = FirebaseInstanceId.getInstance().getToken();


    }

    public void sendRegistrationToServer(final String token, final Context context) {

        try
        {
            Log.d("HAR","ayannidhar");
            StringRequest request = new StringRequest(Request.Method.POST,
                    GlobalMethods.getURL()+"Login/Firebase", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("HAR", error.toString());
                    Log.d("HAR", "Error");
                }
            }){
                @Override
                protected Map<String,String> getParams()throws AuthFailureError {
                    Map<String,String> parameters = new HashMap<>();
                    parameters.put("token",token);
                    return parameters;
                }
            };
            MySingleton.getInstance(context).addToRequestQueue(request);
        }catch (Exception ex){
            Log.d("HAR",ex.toString());
        }

    }
}

