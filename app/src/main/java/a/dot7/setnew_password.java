package a.dot7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import a.common.GlobalMethods;
import a.common.MySingleton;

public class setnew_password extends Activity implements View.OnClickListener {

    AppCompatButton UpdatePasswordButton;
    EditText PasswordText;
    String Contact,Password;
    Intent intent;
    String StatusCode;
    String url = "http://192.168.43.92:3000/Login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setnew_password);
        intent = getIntent();
        UpdatePasswordButton = findViewById(R.id.Update_Password);
        PasswordText = findViewById(R.id.newp);
        Contact = intent.getStringExtra("Contact");
        UpdatePasswordButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        Password = PasswordText.getText().toString();
        callService();
    }
    private void callService()
    {
        try {
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.
                    Listener<String>() {

                @Override
                public void onResponse(String s) {
                    StatusCode = GlobalMethods.GetSubString(s);
                    Log.d("HAR", s);
                    Log.d("HAR", "Satus code:" + StatusCode);
                    //Log.d("HAR",StatusCode);

                    // **********stop progress bar*************************


                    if (StatusCode.contains("302")) {
                        startActivity(new Intent(setnew_password.this, Login.class));
                    }
                    else {

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.d("HAR", volleyError.toString());
                    Log.d("HAR", "Error");
                    //Nitish and pooja, handle this error with a alert box or something
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("LoginID", Contact);
                    parameters.put("password", Password);
                    return parameters;
                }
            };
            MySingleton.getInstance(this).addToRequestQueue(request);
            //Log.d("HAR", "Service ab return kr ri hai");

        } catch (Exception ex) {

        }
    }

}

