package a.Lifecycle_Restaurant_Ordering;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;

import a.dot7.R;
import a.getter_setter.RestaurantOrders;

/**
 * Created by Pooja on 09-04-2018.
 */


public class Your_Orders extends AppCompatActivity{

    private RecyclerView orders_recyclerView;
    private DrawerLayout mDrawerLayout;
    private RecyclerView.LayoutManager  Layout;
    ArrayList<RestaurantOrders> AllRowData;
    YourOrderAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar=findViewById(R.id.Cart_Toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        set_RecyclerView_Details();
        fillTempData();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return true;
        }
    }

    private void set_RecyclerView_Details()
    {
        AllRowData = new ArrayList<>();
       orders_recyclerView = findViewById(R.id.your_order);
        orders_recyclerView.setHasFixedSize(true);
        Layout = new LinearLayoutManager(this);
        orders_recyclerView.setLayoutManager(Layout);
    }
    private void fillTempData()
    {
        ArrayList<RestaurantOrders> restaurantOrders = new ArrayList<>();
        RestaurantOrders addOrder;
        addOrder = new RestaurantOrders();
        addOrder.setRName("Celebrations");
        addOrder.setOrderDate("April 10, 2018");
        addOrder.setOrderId("#123456");
        addOrder.setTotalPrice("530");
        restaurantOrders.add(addOrder);

        addOrder = new RestaurantOrders();
        addOrder.setRName("Rolls Mania");
        addOrder.setOrderDate("April 10, 2018");
        addOrder.setOrderId("#123457");
        addOrder.setTotalPrice("230");
        restaurantOrders.add(addOrder);
        addOrder = new RestaurantOrders();
        addOrder.setRName("Pind Balluchi");
        addOrder.setOrderDate("April 10, 2018");
        addOrder.setOrderId("#123458");
        addOrder.setTotalPrice("730");
        restaurantOrders.add(addOrder);
        addOrder = new RestaurantOrders();
        addOrder.setRName("Old School");
        addOrder.setOrderDate("April 10, 2018");
        addOrder.setOrderId("#123459");
        addOrder.setTotalPrice("1200");
        restaurantOrders.add(addOrder);
        addOrder = new RestaurantOrders();
        addOrder.setRName("Well Beans");
        addOrder.setOrderDate("April 10, 2018");
        addOrder.setOrderId("#123460");
        addOrder.setTotalPrice("130");
        restaurantOrders.add(addOrder);
        adapter = new YourOrderAdapter(this,restaurantOrders);
        orders_recyclerView.setAdapter(adapter);

    }
}

