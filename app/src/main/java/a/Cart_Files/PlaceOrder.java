package a.Cart_Files;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by TUSHAR on 10-04-18.
 */

public class PlaceOrder {
    IndividualRestaurantData Restaurants;
    ArrayList<IndividualRestaurantData> AllRestaurants;
    Context context;
    int size,Rsize;
    ArrayList<DishesData> dishesDetails;
    public PlaceOrder(Context context,ArrayList<IndividualRestaurantData> restaurants)
    {
        this.context = context;
        this.AllRestaurants = restaurants;
        Rsize = this.AllRestaurants.size();
    }
    public String parseDishToJSON( ArrayList<DishesData> dishesDetails)
    {
        size = dishesDetails.size();
        String dishes="[";
        for(int i=0;i<size;i++)
        {
            dishes = dishes + "{\"" + "key\": " + "\"" +
                    dishesDetails.get(i).getDishKey() + "\",\"quantity\": " + dishesDetails.get(i).getQuantity() + "\"}";
            if(i<size-1)
                dishes = dishes + ",";
        }
        dishes = dishes + "]";
        Log.d("HAR","Dishes Json: "+dishes);
        return dishes;

    }
    public String parseRestaurantToJSON()
    {
        String Rdata = "[";
        for(int i=0;i<Rsize;i++)
        {
            Rdata = Rdata + "{\"" + "Rkey\": " + "\"" +
                    AllRestaurants.get(i).getRKey() + "\",\"products\": "
                    + parseDishToJSON(AllRestaurants.get(i).getRDishes()) + "\",\"totalPrice\": "
            + String.valueOf(AllRestaurants.get(i).getRBill()) + "\"}";
            if(i<Rsize-1)
                Rdata = Rdata + ",";
        }
        Rdata = Rdata + "]";
        Log.d("HAR","Restaurants Json: "+Rdata);
        return Rdata;
    }


}
