package a.Cart_Files;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import a.Lifecycle_Restaurant_Ordering.Cart_Items;
import a.getter_setter.Cart;

/**
 * Created by TUSHAR on 09-04-18.
 */

public class ManageData {
    Context context;
    ArrayList<IndividualRestaurantData> restaurants;
    ArrayList<Cart> CartItems;
    int size,totalRestaurants;
    int dishVisit[];
    ArrayList<DishesData> dishes;
    int totalBill,bill;
    public ManageData(Context context, ArrayList<IndividualRestaurantData> restaurants)
    {
        this.context = context;
        this.restaurants = restaurants;
        CartItems = Cart_Items.getInstance(context).getCartItems();
        totalRestaurants = 0;
        totalBill = 30;
      //  this.restaurants = new ArrayList<>();
        size = Cart_Items.getInstance(context).getTotalDishes();
        Log.d("HAR","Total dishes: "+size);
    }
    public int setAllData()
    {
        Log.d("HAR","Cart::");
        dishVisit = new int[size];
        for(int i=0;i<size;i++)
            dishVisit[i]=0;
        for(int i=0;i<size;i++)
        {
            if(dishVisit[i]==0)
            {
                //dishVisit[i]=1;
                totalRestaurants++;
                IndividualRestaurantData NewRestaurant = new IndividualRestaurantData();
                NewRestaurant.setRName(CartItems.get(i).getResName());
                NewRestaurant.setRKey(CartItems.get(i).getResKey());
                NewRestaurant.setRDishes(getAllDishes(NewRestaurant.getRKey()));
                NewRestaurant.setRBill(bill);
                restaurants.add(NewRestaurant);
                Log.d("HAR","Restaurant: "+CartItems.get(i).getResName());
            }
        }
        return totalRestaurants;
    }

    private ArrayList<DishesData> getAllDishes(String rKey) {
        dishes = new ArrayList<>();
        bill = 0;
        for(int i=0;i<size;i++)
        {
            if(dishVisit[i]==0 && CartItems.get(i).getResKey().equals(rKey))
            {
                dishVisit[i] = 1;
                DishesData NewDish = new DishesData();
                NewDish.setDishKey(CartItems.get(i).getDishKey());
                NewDish.setDishName(CartItems.get(i).getDishName());
                NewDish.setDishPrice(CartItems.get(i).getDishPrice());
                NewDish.setQuantity(CartItems.get(i).getQuantity());
                dishes.add(NewDish);
                int totalCostofDish = ( Integer.parseInt(CartItems.get(i).getDishPrice().substring(4))
                        * CartItems.get(i).getQuantity() );
                bill = bill + totalCostofDish;
                Log.d("HAR","Dish: "+CartItems.get(i).getDishName());
                totalBill = totalBill + totalCostofDish;
            }
        }

        return dishes;
    }
    public ArrayList<IndividualRestaurantData> getOderData()
    {
        return restaurants;
    }
}
