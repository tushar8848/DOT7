package a.Cart_Files;

import java.util.ArrayList;

/**
 * Created by TUSHAR on 09-04-18.
 */

public class IndividualRestaurant {

    private String RName;
    private  String RKey;
    ArrayList<DishesDetails> RDishes;

    public void setRName(String rName)
    {
        this.RName = rName;
    }

    public void setRKey(String rKey)
    {
        this.RKey = rKey;
    }

    public void setRDishes(ArrayList<DishesDetails> rDishes)
    {
        this.RDishes = rDishes;
    }

    public String getRName()
    {
        return RName;
    }

    public String getRKey()
    {
        return this.RKey;
    }

    public ArrayList<DishesDetails> getRDishes()
    {
        return this.RDishes;
    }
}
