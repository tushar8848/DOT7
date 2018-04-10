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

    private EditText FlatNo, StreetName, LandMark;
    String Flatno, Street, Landmark;
    private AppCompatButton PlaceOrder;
    private int flat = 0, sname=0, Land = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_order);
        setDetails();
        Validate();

    }

    public void setDetails()
    {
        FlatNo = findViewById(R.id.Flat_Num);
        StreetName = findViewById(R.id.street);
        LandMark = findViewById(R.id.landmark);
        PlaceOrder = findViewById(R.id.appCompatButton2);
        PlaceOrder.setOnClickListener(this);
        Flatno = FlatNo.getText().toString();
        Street = StreetName.getText().toString();
        Landmark = LandMark.getText().toString();
    }

    public void Validate()
    {
           if(Flatno.length()<0)
           {
               FlatNo.setError("This field is required");
           }
           else
               flat = 1;

           if(Street.length()<0)
           {
                StreetName.setError("This field is required");
           }
           else
               sname = 1;

           if(Landmark.length()<0)
           {
               LandMark.setError("This field is required");
           }
           else
               Land = 1;

    }

    @Override
    public void onClick(View v) {

        if(flat == 1 && sname == 1 && Land ==1)
        {
            
        }

    }
}
