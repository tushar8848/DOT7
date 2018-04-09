package a.Cart_Files;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import a.dot7.R;
import a.getter_setter.Cart;
import a.Lifecycle_Restaurant_Ordering.Cart_Items;
/**
 * Created by TUSHAR on 09-04-18.
 */

public class Cart_Page extends AppCompatActivity {

    private RecyclerView CartView;
    private RecyclerView.LayoutManager  CartLayout;
    private CartRestaurantAdapter cartRestaurantAdapter;
    private ArrayList<IndividualRestaurant> Restaurants;
    ManageData data;
    int totalRestaurants;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        //*****************************************************************set the layout file********************************
        setContentView(R.layout.activity_restaurant_recycler_view);
        setRecyclerViewDetails();
        initiateCartView();
        data = new ManageData(this,Restaurants);
        totalRestaurants = data.setAllData();
    }
    private void setRecyclerViewDetails()
    {
        CartView = findViewById(R.id.Recycler_View);
        CartView.setHasFixedSize(true);
        CartLayout = new LinearLayoutManager(this);
        CartView.setLayoutManager(CartLayout);
    }
    private void initiateCartView()
    {
        cartRestaurantAdapter = new CartRestaurantAdapter();

    }
}
