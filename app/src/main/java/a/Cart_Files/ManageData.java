package a.Cart_Files;

import android.content.Context;

import java.util.ArrayList;

import a.Lifecycle_Restaurant_Ordering.Cart_Items;
import a.getter_setter.Cart;

/**
 * Created by TUSHAR on 09-04-18.
 */

public class ManageData {
    Context context;
    ArrayList<IndividualRestaurant> restaurants;
    ArrayList<Cart> CartItems;
    int size,totalRestaurants;
    int dishVisit[];
    ArrayList<DishesDetails> dishes;
    public ManageData(Context context, ArrayList<IndividualRestaurant> restaurants)
    {
        this.context = context;
        this.restaurants = restaurants;
        CartItems = Cart_Items.getInstance(context).getCartItems();
        totalRestaurants = 0;
        size = Cart_Items.getInstance(context).getTotalDishes();
    }
    public int setAllData()
    {
        dishVisit = new int[size];
        for(int i=0;i<size;i++)
            dishVisit[i]=0;
        for(int i=0;i<size;i++)
        {
            if(dishVisit[i]==0)
            {
                dishVisit[i]=1;
                totalRestaurants++;
                IndividualRestaurant NewRestaurant = new IndividualRestaurant();
                NewRestaurant.setRName(CartItems.get(i).getResName());
                NewRestaurant.setRKey(CartItems.get(i).getResKey());
                NewRestaurant.setRDishes(getAllDishes(NewRestaurant.getRKey()));
                restaurants.add(NewRestaurant);

            }
        }
        return totalRestaurants;
    }

    private ArrayList<DishesDetails> getAllDishes(String rKey) {
        dishes = null;
        for(int i=0;i<size;i++)
        {
            if(dishVisit[i]==0 && CartItems.get(i).getResKey().equals(rKey))
            {
                dishVisit[i] = 1;
                DishesDetails NewDish = new DishesDetails();
                NewDish.setDishKey(CartItems.get(i).getDishKey());
                NewDish.setDishName(CartItems.get(i).getDishName());
                NewDish.setDishPrice(CartItems.get(i).getDishPrice());
                NewDish.setQuantity(CartItems.get(i).getQuantity());
                dishes.add(NewDish);
            }
        }
        return dishes;
    }
}
