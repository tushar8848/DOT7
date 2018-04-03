package a.home_screen;

/**
 * Created by TUSHAR on 02-04-18.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import a.common.GlobalMethods;
import a.common.MySingleton;
import a.dot7.R;
import a.getter_setter.Restaurant_Each_Row_data;


public class Restaurant_Recycler_View extends Activity {

    String Contact;
    private RecyclerView Restaurant_recycler_view;
    private List<Restaurant_Each_Row_data> AllRowData;
    private RecyclerView.LayoutManager  Layout;
    private RecyclerView.Adapter Adapter;
    private String URL ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_recycler_view);
        //Restaurant_recycler_view = findViewById(R.id.rec_view);
        Log.d("HAR","Recycler view me aaya");
        set_RecyclerView_Details();
        Log.d("HAR","details set ho gyi");
        getContact();
        URL = GlobalMethods.getURL()+ "Restaurant_Main/" + Contact;
        Json_Data_Web_Call();

    }
    private void getContact()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("logDetails",
                Context.MODE_PRIVATE);
        Contact = sharedPreferences.getString("UserName",null);
    }




    private void set_RecyclerView_Details()
    {
        AllRowData = new ArrayList<>();
        Restaurant_recycler_view = findViewById(R.id.rec_view);
        Restaurant_recycler_view.setHasFixedSize(true);
        Layout = new LinearLayoutManager(this);
        Restaurant_recycler_view.setLayoutManager(Layout);
    }

    public void Json_Data_Web_Call()
    {

       // RequestQueue Queue;
        JsonArrayRequest jsonArrayRequest = new
                JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response1) {

                //Json_Parse_Data(response);
                Log.d("HAR","Response aaya");
                Log.d("HAR",response1.toString());
                String str = response1.toString();
                JSONArray response = null;
                try {
                    response = new JSONArray(str);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Restaurant_Each_Row_data RowData;
                if (response != null) {
                    for ( int i = 0 ; i < response.length() ; i++)
                    {
                        RowData = new Restaurant_Each_Row_data();
                        JSONObject json ;
                        try
                        {
                            json = response.getJSONObject(i);
                            RowData.setRestaurantCuisine(json.getString("cuisines"));
                            RowData.setRestaurantFavflag(json.getString("isFavourite"));
                            RowData.setRestaurantName(json.getString("restaurantName"));
                            RowData.setRestaurantRating(json.getString("rating"));
                            RowData.setRestaurantTiming(json.getString("time"));
                            RowData.setRestaurantImage(json.getString("imageURL"));
                            Log.d("HAR","Restaurant Name: "+ json.getString("restaurantName"));

                        }
                        catch (Exception e) {
                            Log.e("Error: " , String.valueOf(e));
                        }
                        AllRowData.add(RowData);
                    }
                }
                Adapter = new
                        RestaurantView_Adapter(Restaurant_Recycler_View.this,AllRowData);
                Restaurant_recycler_view.setAdapter(Adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError: ",error.toString());
            }
        });

      //  Queue = Volley.newRequestQueue(this);
       // Queue.add(jsonArrayRequest);
        MySingleton.getInstance(this).addToJsonRequestQueue(jsonArrayRequest);
    }

    /*public void Json_Parse_Data(JSONArray array)
    {

    }*/

}
