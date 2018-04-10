package a.Lifecycle_Restaurant_Ordering;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;

import a.dot7.R;

/**
 * Created by HP on 10-04-2018.
 */

public class Address_Recycler_View extends AppCompatActivity implements View.OnClickListener  {

    private EditText FlatNo, StreetName, LandMark, State, District, Pincode;
    private AppCompatButton PlaceOrder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_order);
        setDetails();

    }

    public void setDetails()
    {
        FlatNo = findViewById(R.id.Flat_Num);
        StreetName = findViewById(R.id.StreetName);
        LandMark = findViewById(R.id.Address);
        State = findViewById(R.id.state);
        District = findViewById(R.id.City);
        Pincode = findViewById(R.id.Pincode);
        PlaceOrder = findViewById(R.id.appCompatButton2);
        PlaceOrder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
