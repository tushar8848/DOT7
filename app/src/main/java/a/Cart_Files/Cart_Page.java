package a.Cart_Files;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import a.Lifecycle_Restaurant_Ordering.Address_Recycler_View;
import a.dot7.R;

/**
 * Created by TUSHAR on 09-04-18.
 */

public class Cart_Page extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView CartView;
    private RecyclerView.LayoutManager  CartLayout;
    private CartAdapter cartAdapter;
    private ArrayList<IndividualRestaurantData> Restaurants;
    ManageData data;
    int totalRestaurants;
    TextView TotalBill;
    TextView Gst;
    TextView FinalBill;
    int totalBill;
    double GST,finalBill;
    Button ConfirmOrder;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //*****************************************************************set the layout file********************************
        setContentView(R.layout.activity_cart_page);
        Log.d("HAR","Cart_Page pe aaya");
        setRecyclerViewDetails();
        Restaurants = new ArrayList<>();
        data = new ManageData(this,Restaurants);
        CartData_Singleton.getInstance(this).setData(Restaurants);
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
        ConfirmOrder = findViewById(R.id.ConfirmOrder);
        ConfirmOrder.setOnClickListener(this);



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

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, Address_Recycler_View.class));
    }
}
