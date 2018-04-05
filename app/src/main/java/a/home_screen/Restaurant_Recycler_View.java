package a.home_screen;

/**
 * Created by TUSHAR on 02-04-18.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import a.common.GlobalMethods;
import a.common.MySingleton;
import a.dot7.R;
import a.getter_setter.Restaurant_Each_Row_data;


public class Restaurant_Recycler_View extends AppCompatActivity {

    String Contact;
    private RecyclerView Restaurant_recycler_view;
    private List<Restaurant_Each_Row_data> AllRowData;
    private RecyclerView.LayoutManager  Layout;
    private RecyclerView.Adapter Adapter;
    private String URL ;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_recycler_view);
        Toolbar toolbar = findViewById(R.id.Restaurant_Page_Toolbar);
        mDrawerLayout = findViewById(R.id.Drawer_Layout);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();

        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_18dp);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        //Restaurant_recycler_view = findViewById(R.id.rec_view);
        Log.d("HAR","Recycler view me aaya");
        set_RecyclerView_Details();
        Log.d("HAR","details set ho gyi");
        addRowData();
        getContact();
        URL = GlobalMethods.getURL()+ "Restaurant_Main/" + Contact;

        Json_Data_Web_Call();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.res_view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.Drawer_Layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    private void getContact()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("logDetails",
                Context.MODE_PRIVATE);
        Contact = sharedPreferences.getString("UserName",null);
    }

    private void addRowData()
    {
        //AllRowData = new ArrayList<>();
        Restaurant_Each_Row_data RowData;
        for(int i=0;i<5;i++) {

            RowData = new Restaurant_Each_Row_data();
            RowData.setRestaurantCuisine("Cuisines List");
            RowData.setRestaurantFavflag("0");
            RowData.setRestaurantName("Restaurant Name");
            RowData.setRestaurantRating("4.5");
            RowData.setRestaurantTiming("Timing");
            RowData.setRestaurantImage("https://drive.google.com/uc?authuser=0&id=1YV9-p0r1v7TRlN7QAxhU7SMzdq37XDvc&export=download");
            RowData.setShowShimmer(true);
            AllRowData.add(RowData);
        }
        Adapter = new
                RestaurantView_Adapter(Restaurant_Recycler_View.this,AllRowData);
        Restaurant_recycler_view.setAdapter(Adapter);
    }


    private void set_RecyclerView_Details()
    {
        AllRowData = new ArrayList<>();
        Restaurant_recycler_view = findViewById(R.id.Recycler_View);
        Restaurant_recycler_view.setHasFixedSize(true);
        Layout = new LinearLayoutManager(this);
        Restaurant_recycler_view.setLayoutManager(Layout);
    }

    public void Json_Data_Web_Call()
    {

        //AllRowData = new ArrayList<>();
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
                            RowData.setShowShimmer(true);
                            Log.d("HAR","Restaurant Name: "+ json.getString("restaurantName"));

                        }
                        catch (Exception e) {
                            Log.e("Error: " , String.valueOf(e));
                        }
                        if(i<5)
                            AllRowData.set(i, RowData);
                        else
                            AllRowData.add(RowData);
                    }
                }


                Adapter = new
                        RestaurantView_Adapter(Restaurant_Recycler_View.this,AllRowData);
                Restaurant_recycler_view.setAdapter(Adapter);
                Restaurant_recycler_view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for(Restaurant_Each_Row_data restaurant_each_row_data : AllRowData){
                                restaurant_each_row_data.setShowShimmer(false);
                        }
                        Adapter.notifyDataSetChanged();
                    }
                },3000);

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
