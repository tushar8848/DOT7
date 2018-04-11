package a.Lifecycle_Restaurant_Ordering;

/**
 * Created by TUSHAR on 02-04-18.
 */

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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

import a.Cart_Files.Cart_Page;
import a.common.CheckConnection;
import a.common.GlobalMethods;
import a.common.MySingleton;
import a.dot7.AboutUsTemp;
import a.dot7.About_Us;
import a.dot7.Login;
import a.dot7.R;
import a.dot7.ScreenSlideActivity;
import a.dot7.YourAccount;
import a.getter_setter.Restaurants;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.widget.Toast;

import a.common.MyDialog;


public class Restaurant_Recycler_View extends AppCompatActivity implements View.OnClickListener {

    String Contact;
    final boolean[] DoubleBackPressed = {false};
    String[] Restaurants_name;

    private RecyclerView Restaurant_recycler_view;
    private List<Restaurants> AllRowData;
    private List<String> RestaurantKey, RestaurantImage, RestaurantName, UserSelectedRestaurant;
    private RecyclerView.LayoutManager  Layout;
    private RestaurantsAdapter Adapter = null;
    private String URL,Username, Email, Status, Name ;
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
    boolean SearchProcess = false;

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

        ActionBar actionbar = getSupportActionBar();

        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_drawer);
        }

        mNavigationView = findViewById(R.id.navigation_bar);

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
        if(CheckConnection.getInstance(this).getNetworkStatus()) {
            activateNavBar();
            checkUserLogin();

        }
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

  private void activateNavBar()
  {
      GetNavData();
      mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(MenuItem menuItem) {
              String title="Share your App";

              menuItem.setChecked(true);
              switch (menuItem.getItemId()) {
                  case R.id.your_account:
                      Intent i = new Intent(Restaurant_Recycler_View.this,YourAccount.class);
                      i.putExtra("Name",Name);
                      i.putExtra("Email",Email);
                      i.putExtra("Contact",Username);
                      startActivity(i);
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

                    case R.id.about:
                        startActivity(new Intent(Restaurant_Recycler_View.this,AboutUsTemp.class));
                        return true;
                    case R.id.your_orders:
                        startActivity(new Intent(Restaurant_Recycler_View.this,Your_Orders.class));
                        return true;

                    case R.id.nav_share:
                        try {
                            Intent it = new Intent(Intent.ACTION_SEND);
                            it.setType("text/plain");
                            it.putExtra(Intent.EXTRA_SUBJECT, "DOT7");
                            String sAux = "\nLet me recommend you this application\n\n";
                            sAux = sAux + "https://www.google.com \n\n";
                            it.putExtra(Intent.EXTRA_TEXT, sAux);
                            startActivity(Intent.createChooser(it, "choose one"));
                        } catch(Exception e) {
                            //e.toString();
                        }
                        return true;
                    case R.id.nav_send:
                         Intent sendEmail = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                         sendEmail.setType("text/plain");
                         sendEmail.putExtra(Intent.EXTRA_SUBJECT, "Subject of email");
                        sendEmail.putExtra(Intent.EXTRA_TEXT, "Body of email");
                         sendEmail.setData(Uri.parse("mailto:dot7team@gmail.com")); // or just "mailto:" for blank
                       // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                         if (sendEmail.resolveActivity(getPackageManager()) != null) {
                            startActivity(sendEmail);
                        }
                        //startActivity(sendEmail);
                         return true;


                  default:
                      return true;
              }
          }
      });

  }
    public void call_to_order(View view)
    {
        //Snackbar.make(view, "Call To Order ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel: 8285320858"));
        startActivity(intent);

        Snackbar.make(view, "Call To Order ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }


    /*@SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.restaurant_toolbar_menu, menu);

        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);;
        searchView = (SearchView)  menu.getItem(R.id.Search);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        Log.e("","Searchmmanager create ho gya");

        if (searchView.isSubmitButtonEnabled())
        {
            Log.e("","Yes Enables");
        }
        final SearchView.SearchAutoComplete searchAutoComplete =
                searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setDropDownBackgroundResource(R.color.gray);
        searchAutoComplete.setDropDownAnchor(R.id.Search);
        searchAutoComplete.setThreshold(0);

        populateSearchAdapaterData();
        ArrayAdapter<String> Adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, Restaurants_name);


        searchAutoComplete.setAdapter(Adapter);
        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchAutoComplete.setText(queryString);
                GlobalMethods.print(Restaurant_recycler_view.getContext(),
                        "you clicked " + queryString);
                RestaurantNameselected(queryString);

                SearchProcess = true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                AlertDialog alertDialog = new AlertDialog.Builder(Restaurant_recycler_view.getContext()).create();
                alertDialog.setMessage("Search keyword is " + query);
                alertDialog.show();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                GlobalMethods.print(Restaurant_recycler_view.getContext(),newText);
                MenuItem cart = menu.findItem(R.id.Cart);
                if(cart.isVisible())
                {
                    cart.setVisible(false);
                }
                return false;
            }
        });


        return true;
    }

    private void populateSearchAdapaterData() {

        for(int i = 0; i < RestaurantName.size(); i++)
        {
            Restaurants_name[i] = RestaurantName.get(i);
        }
    }

    private void RestaurantNameselected(String queryString) {

        int size =  AllRowData.size();
        List<Restaurants> Searched_rows = null;
        for(int  i =0; i < size; i++)
        {
            Restaurants Rowdata = new Restaurants();
            if (AllRowData.get(i).getRestaurantName().contentEquals(queryString))
            {
                Rowdata.setRestaurantName(AllRowData.get(i).getRestaurantName());
                Rowdata.setRestaurantCuisine(AllRowData.get(i).getRestaurantCuisine());
                Rowdata.setRestaurantFavflag(AllRowData.get(i).getRestaurantFavflag());
                Rowdata.setRestaurantImage(AllRowData.get(i).getRestaurantImage());
                Rowdata.setRestaurantRating(AllRowData.get(i).getRestaurantRating());
                Rowdata.setRestaurantTiming(AllRowData.get(i).getRestaurantTiming());
                Rowdata.setShowShimmer(false);
                Searched_rows.add(Rowdata);
                Log.e("Added Restaurant name: ",Rowdata.getRestaurantName());
            }
        }
        Adapter = new
                RestaurantsAdapter(Restaurant_Recycler_View.this,Searched_rows);
        Restaurant_recycler_view.setAdapter(Adapter);

    }
*/

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
                Log.e("HAR","Cart pe click hua");
                startActivity(new Intent(Restaurant_Recycler_View.this,Cart_Page.class));
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
        Log.e("","recyclerview details");
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

                    int length = response.length();
                    Restaurants_name = new String[length];
                    for ( int i = 0 ; i < length ; i++)
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

                            Log.e("Added Restaurant name: ", Restaurants_name[i]);
                            RowData.setShowShimmer(true);

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

                        UserSelectedRestaurant.add(RestaurantKey.get(position));
                       // Snackbar.make(view, "Card "+position+" clicked", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                        Intent intent = new Intent(Restaurant_Recycler_View.this,Individual_Restaurant_Page.class);
                        intent.putExtra("RestaurantKey", RestaurantKey.get(position));
                        intent.putExtra("RestaurantName", RestaurantName.get(position));
                        intent.putExtra("RestaurantImage", RestaurantImage.get(position));
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
                       builder.setMessage("You have been logged out");
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
            Error_Message.setVisibility(View.GONE);
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

        if(SearchProcess)
        {
            SearchProcess = false;
            Adapter = new RestaurantsAdapter(Restaurant_recycler_view.getContext(),AllRowData);
            Restaurant_recycler_view.setAdapter(Adapter);
        }

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
                    Name = jsonObject.getString("name");
                    UserName_Nav.setText(Name);
                    Email = jsonObject.getString("email");
                    UserEmail_Nav.setText(Email);
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
