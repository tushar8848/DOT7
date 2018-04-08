package a.Lifecycle_Restaurant_Ordering;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import a.common.CheckConnection;
import a.common.GlideApp;
import a.common.GlobalMethods;
import a.common.MySingleton;
import a.dot7.R;
import a.getter_setter.Dishes;
import a.getter_setter.Restaurants;

public class Individual_Restaurant_Page extends AppCompatActivity implements View.OnClickListener {

    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;
    private RecyclerView recyclerView;
    private boolean appBarExpanded = true;
    private DishesAdapter dishesAdapter;
    private RecyclerView.LayoutManager  Layout;
    private List<Dishes> data;
    private Intent intent;
    private String URL;
    private ImageView BannerImage;
    private Menu collapsedMenu;
    Snackbar snackbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_restaurant_page);
        intent = getIntent();

        final Toolbar toolbar = findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);

        BannerImage = findViewById(R.id.header);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appBarLayout = findViewById(R.id.appbar);

        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(intent.getStringExtra("RestaurantName"));

        GlideApp
                .with(Individual_Restaurant_Page.this)
                .load(intent.getStringExtra("RestaurantImage"))
                .centerCrop()
                .into(BannerImage);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.dot7_banner);

        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @SuppressWarnings("ResourceType")
            @Override
            public void onGenerated(Palette palette) {
                int vibrantColor = palette.getVibrantColor(R.color.primary_500);
                collapsingToolbar.setContentScrimColor(vibrantColor);
                collapsingToolbar.setStatusBarScrimColor(R.color.black_trans80);
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d(Individual_Restaurant_Page.class.getSimpleName(), "onOffsetChanged: verticalOffset: " + verticalOffset);

                //  Vertical offset == 0 indicates appBar is fully expanded.
                if (Math.abs(verticalOffset) > 200) {
                    appBarExpanded = false;
                    invalidateOptionsMenu();
                } else {
                    appBarExpanded = true;
                    invalidateOptionsMenu();
                }
            }
        });

        set_RecyclerView_Details();
        addRowData();
        GetProducts();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_settings:
                return true;
        }
        if (item.getTitle() == "Add") {
            Toast.makeText(this, "Added to favourites", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (collapsedMenu != null
                && (!appBarExpanded || collapsedMenu.size() != 1)) {
            //collapsed
            collapsedMenu.add("Add")
                    .setIcon(R.drawable.unfavourite)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        } else {
            //expanded
        }
        return super.onPrepareOptionsMenu(collapsedMenu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.res_view_menu, menu);
        collapsedMenu = menu;
        return true;
    }

    private void set_RecyclerView_Details()
    {
        data = new ArrayList<>();
        recyclerView =  findViewById(R.id.scrollableview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public void onClick(View v) {

    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void addRowData()
    {
       // data = new ArrayList<>();
       // Restaurants RowData;
        /*for(int i=0;i<5;i++) {

            RowData = new Restaurants();
            RowData.setRestaurantCuisine("Cuisines List");
            RowData.setRestaurantFavflag("0");
            RowData.setRestaurantName("Restaurant Name");
            RowData.setRestaurantRating("4.5");
            RowData.setRestaurantTiming("Timing");
            RowData.setRestaurantImage("https://drive.google.com/TRlN7QAxhU7SMzdq37XDvc&export=download");
            RowData.setShowShimmer(true);
            AllRowData.add(RowData);
        }*/
        dishesAdapter = new
                DishesAdapter(Individual_Restaurant_Page.this,data,snackbar);
        recyclerView.setAdapter(dishesAdapter);

    }

    public void GetProducts()
    {
        URL = GlobalMethods.getURL() + "Product/" + intent.getStringExtra("RestaurantKey");
        //AllRowData = new ArrayList<>();
        // RequestQueue Queue;
        //determineService = 2;
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
                Dishes RowData;
                if (response != null) {
                    for ( int i = 0 ; i < response.length() ; i++)
                    {
                        RowData = new Dishes();
                        JSONObject json ;
                        try
                        {
                            json = response.getJSONObject(i);
                            //RowData.setShowShimmer(true);
                            RowData.setDishName(json.getString("productName"));
                            RowData.setDishImageUrl(json.getString("productImage"));
                            RowData.setDishAvailability(json.getString("availabilityFlag"));
                            RowData.setDishPrice(json.getString("price"));
                            RowData.setDishVFlag(json.getString("veg_nVeg_Flag"));

                        }
                        catch (Exception e) {
                            Log.e("Error: " , String.valueOf(e));
                        }
                       /* if(i<5)
                            data.set(i, RowData);
                        else
                            data.add(RowData);
                            */
                       data.add(i,RowData);
                    }
                }


                dishesAdapter = new
                        DishesAdapter(Individual_Restaurant_Page.this,data,snackbar);
                recyclerView.setAdapter(dishesAdapter);
                dishesAdapter.setOnItemClickListener(new DishesAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                    }
                });



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(CheckConnection.getInstance(Individual_Restaurant_Page.this).getNetworkStatus())
                {
                    Log.e("VolleyError: ",error.toString());
                }
                else {
                  //  recyclerView.setVisibility(View.GONE);
                  //  Error_Image.setVisibility(View.VISIBLE);
                   // Error_Button.setVisibility(View.VISIBLE);
                    //******************************************set error internet connection image*********************************
                }
               Log.d("HAR",error.toString());
            }
        });

        //  Queue = Volley.newRequestQueue(this);
        // Queue.add(jsonArrayRequest);
        MySingleton.getInstance(this).addToJsonRequestQueue(jsonArrayRequest);
    }

    private void quantityModifier()
    {

    }




}
