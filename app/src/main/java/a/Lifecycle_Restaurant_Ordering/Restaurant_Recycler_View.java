package a.Lifecycle_Restaurant_Ordering;

/**
 * Created by TUSHAR on 02-04-18.
 */

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.SearchView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import a.common.CheckConnection;
import a.common.GlobalMethods;
import a.common.MySingleton;
import a.common.OTP_Reader;
import a.dot7.Login;
import a.dot7.R;
import a.dot7.ScreenSlideActivity;
import a.dot7.myProfile;
import a.getter_setter.Restaurants;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import a.common.MyDialog;


public class Restaurant_Recycler_View extends AppCompatActivity implements View.OnClickListener {

    String Contact;
    final boolean[] DoubleBackPressed = {false};
    List<String> Restaurants_name;

    private RecyclerView Restaurant_recycler_view;
    private List<Restaurants> AllRowData;
    private List<String> RestaurantKey, RestaurantImage, RestaurantName, UserSelectedRestaurant;
    private RecyclerView.LayoutManager  Layout;
    private RestaurantsAdapter Adapter = null;
    private String URL,Username, Email, Status ;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private int mCurrentSelectedPosition;
    private AlertDialog Dialog;
    View view;
    ImageView Error_Image;
    TextView Error_Message, UserName_Nav, UserEmail_Nav;
    Button Error_Button;
    private NavigationView navigationView;
    String url = GlobalMethods.getURL() + "Login";
    AlertDialog CustomDialog;
    int determineService = 1;
    SearchView searchView = null;
   // FloatingActionButton fab = findViewById(R.id.fab);

    private boolean isFabOPEN=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_recycler_view);
        Toolbar toolbar = findViewById(R.id.Restaurant_Page_Toolbar);
         navigationView =  findViewById(R.id.navigation_bar);
        View header = navigationView.getHeaderView(0);
        mDrawerLayout = findViewById(R.id.Drawer_Layout);
        Error_Image = findViewById(R.id.Restaurant_View_Error);
        Error_Message = findViewById(R.id.Restaurant_Error_Message);
        Error_Button = findViewById(R.id.Restaurant_View_RetryButton);
        Log.e("","SearchIcon mil gya");
        UserName_Nav = header.findViewById(R.id.UserName);
        UserEmail_Nav = header.findViewById(R.id.UserEmail);
        Error_Button.setOnClickListener(Restaurant_Recycler_View.this);
        setSupportActionBar(toolbar);
        setSharedPreference();
        GetNavData();
        ActionBar actionbar = getSupportActionBar();

        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_drawer);
        }

        mNavigationView = findViewById(R.id.navigation_bar);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(Restaurant_Recycler_View.this,myProfile.class));
                        mCurrentSelectedPosition = 0;
                        return true;
                    case R.id.your_account:
                        Snackbar.make(view, "Item Two", Snackbar.LENGTH_SHORT).show();
                        mCurrentSelectedPosition = 1;
                        return true;
                    case R.id.logout:
                        AlertDialog.Builder builder=new AlertDialog.Builder(Restaurant_Recycler_View.this);
                        builder.setMessage("Are you sure you want to logout?");
                        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences sharedPreferences = getSharedPreferences("logDetails",
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("UserName", null);
                                editor.putString("Password",null);
                                editor.commit();
                                startActivity(new Intent(Restaurant_Recycler_View.this, ScreenSlideActivity.class));
                                dialogInterface.cancel();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        CustomDialog=builder.create();
                        CustomDialog.show();

                        return true;

                    default:
                        return true;
                }
            }
        });

       /* FloatingActionButton fab = findViewById(R.id.fab);
        view = fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isFabOPEN)
                {
                    showfab();
                }
                else
                    closefab();

            }
        });*/

        //Restaurant_recycler_view = findViewById(R.id.rec_view);
        Log.d("HAR","Recycler view me aaya");
        set_RecyclerView_Details();
        Log.d("HAR","details set ho gyi");
        addRowData();
        getContact();
        URL = GlobalMethods.getURL()+ "Restaurant_Main/" + Contact;
        if(CheckConnection.getInstance(this).getNetworkStatus())
            checkUserLogin();
        else
        {
            Restaurant_recycler_view.setVisibility(View.GONE);
            Error_Image.setVisibility(View.VISIBLE);
            Error_Message.setVisibility(View.VISIBLE);
            Error_Button.setVisibility(View.VISIBLE);
            //******************************************set error internet connection image*********************************
        }


    }
  /*  private void showfab()
    {
        FloatingActionButton fab_call = findViewById(R.id.fab_call_to_order);
        isFabOPEN=true;

        fab_call.animate().translationY(-getResources().getDimension( R.dimen.standard_55));
        Snackbar.make(view, "Replace with your own action .....", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
    private void closefab()
    {
        FloatingActionButton fab_call = findViewById(R.id.fab_call_to_order);
        isFabOPEN=false;
        fab_call.animate().translationY(0);
    }*/

    public void call_to_order(View view)
    {
        //Snackbar.make(view, "Call To Order ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel: 8285320858"));
        startActivity(intent);

        Snackbar.make(view, "Call To Order ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.restaurant_toolbar_menu, menu);
        searchView = (SearchView) menu.findItem(R.id.Search).getActionView();
        Log.e("","create kr liya");
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.e("id of item selected: ",String.valueOf(item.getItemId()));

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.Search:
                Log.e("","Search icon clicked");
                return true;
            case R.id.Cart:
                Log.e("","Cart pe click hua");
              //  startActivity(new Intent(Restaurant_Recycler_View.this,activity_cart.class));
        }
        return super.onOptionsItemSelected(item);
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
        Restaurants RowData;
        for(int i=0;i<5;i++) {

            RowData = new Restaurants();
            RowData.setRestaurantCuisine("Cuisines List");
            RowData.setRestaurantFavflag("0");
            RowData.setRestaurantName("Restaurant Name");
            RowData.setRestaurantRating("4.5");
            RowData.setRestaurantTiming("Timing");
            RowData.setRestaurantImage("https://drive.google.com/TRlN7QAxhU7SMzdq37XDvc&export=download");
            RowData.setShowShimmer(true);
            AllRowData.add(RowData);
        }
        Adapter = new
                RestaurantsAdapter(Restaurant_Recycler_View.this,AllRowData);
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
        RestaurantKey = new ArrayList<>();
        RestaurantName = new ArrayList<>();
        RestaurantImage = new ArrayList<>();
        UserSelectedRestaurant = new ArrayList<>();
        //AllRowData = new ArrayList<>();
       // RequestQueue Queue;
        determineService = 2;
        JsonArrayRequest jsonArrayRequest = new
                JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response1) {
                JSONArray response = null;
                JSONObject json;
                //Json_Parse_Data(response);
                Log.d("HAR","Response aaya");
                Log.d("HAR",response1.toString());
                String str = response1.toString();

                try {
                    response = new JSONArray(str);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Restaurants RowData;
                if (response != null) {

                    for ( int i = 0 ; i < response.length() ; i++)
                    {
                        RowData = new Restaurants();

                        try
                        {
                            json = response.getJSONObject(i);
                            RowData.setRestaurantCuisine(json.getString("cuisines"));
                            RowData.setRestaurantFavflag(json.getString("isFavourite"));
                            RowData.setRestaurantName(json.getString("restaurantName"));
                            RowData.setRestaurantRating(json.getString("rating"));
                            RowData.setRestaurantTiming(json.getString("time"));
                            RowData.setRestaurantImage(json.getString("imageURL"));
                            RestaurantKey.add(json.getString("key"));
                            RestaurantName.add(json.getString("restaurantName"));
                            RestaurantImage.add(json.getString("imageURL"));


                            Restaurants_name.add(json.getString("restaurantName"));                          // for searching
                            Log.e("Added Restaurant name: ",RowData.getRestaurantName());
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
                        RestaurantsAdapter(Restaurant_Recycler_View.this,AllRowData);
                Restaurant_recycler_view.setAdapter(Adapter);


                Adapter.setOnItemClickListener(new RestaurantsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                        UserSelectedRestaurant.add(RestaurantKey.get(position).toString());
                       // Snackbar.make(view, "Card "+position+" clicked", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                        Intent intent = new Intent(Restaurant_Recycler_View.this,Individual_Restaurant_Page.class);
                        intent.putExtra("RestaurantKey", RestaurantKey.get(position).toString());
                        intent.putExtra("RestaurantName", RestaurantName.get(position).toString());
                        intent.putExtra("RestaurantImage", RestaurantImage.get(position).toString());
                        startActivity(intent);
                    }
                });
                Restaurant_recycler_view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for(Restaurants restaurant_each_row_data : AllRowData){
                                restaurant_each_row_data.setShowShimmer(false);
                        }
                        Adapter.notifyDataSetChanged();
                    }
                },3000);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(CheckConnection.getInstance(Restaurant_Recycler_View.this).getNetworkStatus())
                {
                    Log.e("VolleyError: ",error.toString());
                }
                else {
                    Restaurant_recycler_view.setVisibility(View.GONE);
                    Error_Image.setVisibility(View.VISIBLE);
                    Error_Button.setVisibility(View.VISIBLE);
                    //******************************************set error internet connection image*********************************
                }
            }
        });

      //  Queue = Volley.newRequestQueue(this);
       // Queue.add(jsonArrayRequest);
        MySingleton.getInstance(this).addToJsonRequestQueue(jsonArrayRequest);
    }

   private void checkUserLogin()
   {
       SharedPreferences sharedPreferences = getSharedPreferences("logDetails",
               Context.MODE_PRIVATE);
       final String  UserName = sharedPreferences.getString("UserName",null);
       final String Password = sharedPreferences.getString("Password",null);
       try {
           StringRequest request = new StringRequest(Request.Method.POST, url, new Response.
                   Listener<String>() {

               @Override
               public void onResponse(String s) {
                   String StatusCode = GlobalMethods.GetSubString(s);
                   Log.d("HAR", s);
                   Log.d("HAR", "Satus code:" + StatusCode);
                   //Log.d("HAR",StatusCode);

                   // **********stop progress bar*************************


                   if (StatusCode.contains("302")) {
                       // GlobalMethods.print(SplashActivity.this, "Data Found");
                       // Intent intent = new Intent(SplashActivity.this, TempActivity.class);
                       Json_Data_Web_Call();


                   } else {

                       AlertDialog.Builder builder=new AlertDialog.Builder(Restaurant_Recycler_View.this);
                       builder.setMessage("You have logged out");
                       builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               dialogInterface.cancel();
                               startActivity(new Intent(Restaurant_Recycler_View.this, Login.class));
                           }
                       });
                       CustomDialog=builder.create();
                       CustomDialog.show();


                   }
               }
           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError volleyError) {
                   if(!(CheckConnection.getInstance(Restaurant_Recycler_View.this).getNetworkStatus()))

                   {
                       Restaurant_recycler_view.setVisibility(View.GONE);
                       Error_Image.setVisibility(View.VISIBLE);
                       Error_Button.setVisibility(View.VISIBLE);
                       //******************************************set error internet connection image*********************************
                   }
                   Log.d("HAR", volleyError.toString());
                   Log.d("HAR", "Error");
                   //Nitish and pooja, handle this error with a alert box or something
               }
           }) {
               @Override
               protected Map<String, String> getParams() throws AuthFailureError {
                   Map<String, String> parameters = new HashMap<>();
                   parameters.put("LoginID", UserName);
                   parameters.put("password", Password);
                   // if(Name != null)
                   //   parameters.put("Name",Name);
                   return parameters;
               }
           };
           MySingleton.getInstance(this).addToRequestQueue(request);

           Log.d("HAR", "Service ab return kr ri hai");

       } catch (Exception ex) {

       }

   }

    @Override
    public void onClick(View v) {
        if(CheckConnection.getInstance(this).getNetworkStatus()) {
            Error_Image.setVisibility(View.GONE);
            Error_Button.setVisibility(View.GONE);
            Restaurant_recycler_view.setVisibility(View.VISIBLE);
            if(determineService == 1)
            checkUserLogin();
            else
                Json_Data_Web_Call();
        }

    }

    @Override
    public void onBackPressed() {

        Log.e("back button: ",String.valueOf(DoubleBackPressed[0]));

        DrawerLayout drawer =  findViewById(R.id.Drawer_Layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            if (DoubleBackPressed[0]) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            else
            {
                DoubleBackPressed[0] = true;
                GlobalMethods.print(this,"Please Click again to exit!");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        DoubleBackPressed[0] = false;
                    }
                },2000);
            }
        }
    }

    public void SetSharedValues()
    {
        SharedPreferences sharedPreferences =
                getSharedPreferences("logDetails",this.MODE_PRIVATE);
        Username = sharedPreferences.getString("Name",null);
        Email = sharedPreferences.getString("Email",null);

    }

    public void GetNavData()
    {

        Log.d("HAR",Username);
        String URI = GlobalMethods.getURL()+"Restaurant_Main/GetNavData?id="+Username;
        //AllRowData = new ArrayList<>();
        // RequestQueue Queue;

        JsonArrayRequest jsonArrayRequest = new
                JsonArrayRequest(URI,
                new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response1) {
                JSONArray response = null;
                JSONObject json;
                //Json_Parse_Data(response);
                Log.d("HAR", "Response navbar valla aaya");
                Log.d("HAR", response1.toString());
                String str = response1.toString();
                JSONObject jsonObject = null;
                try {
                    response = new JSONArray(str);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                     jsonObject = response.getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    UserName_Nav.setText(jsonObject.getString("name"));
                    UserEmail_Nav.setText(jsonObject.getString("email"));
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("HAR","nav JSON error");
                    Log.d("HAR",e.toString());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("HAR","nav error");
                Log.d("HAR",error.toString());

            }
        });

        //  Queue = Volley.newRequestQueue(this);
        // Queue.add(jsonArrayRequest);
        MySingleton.getInstance(this).addToJsonRequestQueue(jsonArrayRequest);
    }

    public void setSharedPreference()
    {
        SharedPreferences sp = getSharedPreferences("logDetails", a.dot7.Login.MODE_PRIVATE);
        Username = sp.getString("UserName",null);

    }

}
