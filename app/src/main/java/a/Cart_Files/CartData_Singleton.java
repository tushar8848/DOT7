package a.Cart_Files;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by TUSHAR on 10-04-18.
 */

public class CartData_Singleton {
    Context context;
    ArrayList<IndividualRestaurantData> data;
    static CartData_Singleton cartData_singleton;
    CartData_Singleton(Context context)
    {
        this.context = context;

    }
    public static CartData_Singleton getInstance(Context context)
    {
        if(cartData_singleton == null)
        {
            cartData_singleton = new CartData_Singleton(context);
        }
        return cartData_singleton;
    }
    public ArrayList<IndividualRestaurantData> getData()
    {
        return data;
    }
    public void setData(ArrayList<IndividualRestaurantData> data)
    {
        this.data = data;
    }
}
