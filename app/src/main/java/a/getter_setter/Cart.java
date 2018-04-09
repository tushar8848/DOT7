package a.getter_setter;

/**
 * Created by TUSHAR on 09-04-18.
 */

public class Cart {
    public String RName = null;
    public String DName = null;
    public int DQuantity = 0;
    public String Dprice = null;
    public String Rkey = null;
    public String Dkey = null;

    public String getDishName()
    {
        return DName;
    }
    public String getResName()
    {
        return RName;
    }
    public String getDishPrice()
    {
        return Dprice;
    }
    public int getQuantity(){ return  DQuantity; }
    public String getDishKey(){ return Dkey;}
    public String getResKey(){ return Rkey;}
    public void setDishName(String Name)
    {
        this.DName = Name;
    }
    public void setDishPrice(String price)
    {
        this.Dprice = price;
    }
    public void setQuantity(int quantity){ this.DQuantity = quantity; }
    public void setDishKey(String key){ this.Dkey = key; }
    public void setResName(String Name)
    {
        this.RName = Name;
    }
    public void setResKey(String key){ this.Rkey = key; }
}
