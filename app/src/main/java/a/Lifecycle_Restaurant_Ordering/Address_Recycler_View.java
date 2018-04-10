package a.Lifecycle_Restaurant_Ordering;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import a.common.GlobalMethods;
import a.common.MySingleton;
import a.common.OTP_Generator;
import a.common.OTP_Reader;
import a.dot7.R;
import a.dot7.Register;

/**
 * Created by HP on 10-04-2018.
 */

public class Address_Recycler_View extends AppCompatActivity implements View.OnClickListener  {

    private EditText FlatNo, StreetName, LandMark;
    String Flatno, Street, Landmark, StatusCode, url;
    private AppCompatButton PlaceOrder;
    private int flat = 0, sname=0, Land = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_order);
        setDetails();
        Validate();
        url = GlobalMethods.getURL() + "Restaurant_Main/InsertOrder";

    }

    public void setDetails()
    {
        FlatNo = findViewById(R.id.Flat_Num);
        StreetName = findViewById(R.id.street);
        LandMark = findViewById(R.id.landmark);
        PlaceOrder = findViewById(R.id.appCompatButton2);
        PlaceOrder.setOnClickListener(this);
        Flatno = FlatNo.getText().toString();
        Street = StreetName.getText().toString();
        Landmark = LandMark.getText().toString();
    }

    public void Validate()
    {
           if(Flatno.length()<0)
           {
               FlatNo.setError("This field is required");
           }
           else
               flat = 1;

           if(Street.length()<0)
           {
                StreetName.setError("This field is required");
           }
           else
               sname = 1;

           if(Landmark.length()<0)
           {
               LandMark.setError("This field is required");
           }
           else
               Land = 1;

    }

    @Override
    public void onClick(View v) {

        if(flat == 1 && sname == 1 && Land ==1)
        {
                callOrderService();


        }


    }

    public void callOrderService()
    {
        try {
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.
                    Listener<String>() {

                @Override
                public void onResponse(String s) {
                    // progressBar.setActivated(false);
                    //progressBar.setVisibility(View.GONE);
                    StatusCode = GlobalMethods.GetSubString(s);
                    Log.d("HAR", s);
                    // ********************************************************stop progress bar*************************

                    if (!StatusCode.contains("302")) {


                    } else {
                        Toast.makeText(Address_Recycler_View.this, "Order Unsuccessful",2);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    //progressBar.setVisibility(View.GONE);
                    Toast.makeText(Address_Recycler_View.this, "Order Unsuccessful",2);
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<>();
                   // parameters.put("LoginID", contact);

                    // if(Name != null)
                    //   parameters.put("Name",Name);
                    return parameters;
                }
            };
            MySingleton.getInstance(this).addToRequestQueue(request);


        } catch (Exception ex) {

        }
    }
}
