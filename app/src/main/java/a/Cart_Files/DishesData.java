package a.Cart_Files;

/**
 * Created by TUSHAR on 09-04-18.
 */

public class DishesData {


    private String DishName = null;
    private String Dishprice = null;
    private String DishKey = null;
    private int Dishquantity = 0;


    public String getDishName()
    {
        return DishName;
    }
    public String getDishPrice()
    {
        return Dishprice;
    }
    public int getQuantity(){ return  Dishquantity; }
    public String getDishKey(){ return DishKey;}
    public void setDishName(String Name)
    {
        this.DishName = Name;
    }
    public void setDishPrice(String price)
    {
        this.Dishprice = price;
    }
    public void setQuantity(int quantity){ this.Dishquantity = quantity; }
    public void setDishKey(String key){ this.DishKey = key; }

}
