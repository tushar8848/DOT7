package a.Cart_Files;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import a.dot7.R;

/**
 * Created by TUSHAR on 09-04-18.
 */

public class Cart_Page extends AppCompatActivity {

    private RecyclerView CartView;
    private RecyclerView.LayoutManager  CartLayout;
    private CartAdapter cartAdapter;
    private ArrayList<IndividualRestaurant> Restaurants;
    ManageData data;
    int totalRestaurants;
    TextView TotalBill;
    TextView Gst;
    TextView FinalBill;
    int totalBill;
    double GST,finalBill;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //*****************************************************************set the layout file********************************
        setContentView(R.layout.activity_cart);
        Log.d("HAR","Cart_Page pe aaya");
        setRecyclerViewDetails();
        Restaurants = new ArrayList<>();
        data = new ManageData(this,Restaurants);
        initiateCartView();
        totalRestaurants = data.setAllData();
       TotalBill = findViewById(R.id.totalBill);
        Gst = findViewById(R.id.totalGST);
        FinalBill = findViewById(R.id.FinalBill);
        totalBill = data.totalBill;
        GST = (totalBill*5)/100;
        finalBill = totalBill + GST;
        TotalBill.setText(String.valueOf(totalBill));
        Gst.setText(String.valueOf(GST));
        FinalBill.setText(String.valueOf(finalBill));
        PlaceOrder order = new PlaceOrder(this,Restaurants);
        String rorder = order.parseRestaurantToJSON();

    }

    private void setRecyclerViewDetails()
    {
        CartView = findViewById(R.id.cart_recycler);
        CartView.setHasFixedSize(true);
        CartLayout = new LinearLayoutManager(this);
        CartView.setLayoutManager(CartLayout);
    }
    private void initiateCartView()
    {
        cartAdapter = new CartAdapter(this,Restaurants);
        CartView.setAdapter(cartAdapter);

    }
}
