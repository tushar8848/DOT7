package a.Lifecycle_Restaurant_Ordering;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import a.Cart_Files.CartData_Singleton;
import a.Cart_Files.IndividualRestaurantData;
import a.common.CheckConnection;
import a.common.GlobalMethods;
import a.common.MySingleton;
import a.dot7.R;
import a.Cart_Files.ParseOrder;
/**
 * Created by HP on 10-04-2018.
 */

public class Address_Recycler_View extends AppCompatActivity implements View.OnClickListener  {

    private TextView FlatNo, StreetName, LandMark;
    String Flatno, Street, Landmark, StatusCode, url;
    private AppCompatButton PlaceOrder;
    private int eflat = 0, esname=0, eLand = 0;
    ParseOrder order;
    ArrayList<IndividualRestaurantData> data;
    String JsonStringOrder;
    String UserName;
    AlertDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_order);
        setDetails();
        url = GlobalMethods.getURL() + "Restaurant_Main/InsertOrder";
        data = CartData_Singleton.getInstance(this).getData();
        order = new ParseOrder(this,data);
        JsonStringOrder = order.parseRestaurantToJSON();
        SharedPreferences sharedPreferences = getSharedPreferences("logDetails",
                Context.MODE_PRIVATE);
        UserName = sharedPreferences.getString("UserName",null);
    }

    public void setDetails() {
        FlatNo = findViewById(R.id.Flat_Num_EditText);
        StreetName = findViewById(R.id.streetEditText);
        LandMark = findViewById(R.id.landmarkEditText);
        PlaceOrder = findViewById(R.id.appCompatButton2);
        PlaceOrder.setOnClickListener(this);
    }
    public void getDetails()
    {
        Flatno = FlatNo.getText().toString();
        Street = StreetName.getText().toString();
        Landmark = LandMark.getText().toString();
        Validate();
    }

    public void Validate()
    {
           if(Flatno.length() == 0)
           {
               eflat = 0;
               FlatNo.setError("This field is required");
           }
           else
               eflat = 1;

           if(Street.length() == 0)
           {
                StreetName.setError("This field is required");
                esname = 0;
           }
           else
               esname = 1;

           if(Landmark.length() == 0)
           {
               LandMark.setError("This field is required");
               eLand = 0;
           }
           else
               eLand = 1;

    }

    @Override
    public void onClick(View v) {
        getDetails();
        if(eflat == 1 && esname == 1 && eLand == 1)
        {
            if (CheckConnection.getInstance(this).getNetworkStatus())
                callOrderService();
            else {
                Snackbar.make(v,"No Internet Connection",Snackbar.LENGTH_LONG).show();
            }
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
                        AlertDialog.Builder builder=new AlertDialog.Builder(Address_Recycler_View.this);
                        builder.setMessage("Your order have been placed. Check in Your Orders for the order details");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                startActivity(new Intent(Address_Recycler_View.this, Restaurant_Recycler_View.class));
                            }
                        });
                       dialog = builder.create();
                        dialog.show();

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
                   parameters.put("LoginID",UserName);
                   parameters.put("FlatNo",Flatno);
                   parameters.put("StreetName",Street);
                   parameters.put("Landmark",Landmark);
                   parameters.put("JSONData",JsonStringOrder);
                    return parameters;
                }
            };
            MySingleton.getInstance(this).addToRequestQueue(request);


        } catch (Exception ex) {

        }
    }
}
