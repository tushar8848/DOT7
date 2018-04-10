package a.Cart_Files;

import java.util.ArrayList;

/**
 * Created by TUSHAR on 09-04-18.
 */

public class IndividualRestaurantData {

    private String RName;
    private  String RKey;
    ArrayList<DishesData> RDishes;
    private int restauarntBill;
    public void setRName(String rName)
    {
        this.RName = rName;
    }

    public void setRKey(String rKey)
    {
        this.RKey = rKey;
    }

    public void setRDishes(ArrayList<DishesData> rDishes)
    {
        this.RDishes = rDishes;
    }
    public void setRBill(int bill){this.restauarntBill = bill;}

    public String getRName()
    {
        return RName;
    }

    public String getRKey()
    {
        return this.RKey;
    }

    public ArrayList<DishesData> getRDishes()
    {
        return this.RDishes;
    }
    public int getRBill(){return restauarntBill;}
}
