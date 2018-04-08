package a.getter_setter;

/**
 * Created by TUSHAR on 07-04-18.
 */

public class Dishes {
    private String imageURL = null;
    private String Name = null;
    private String price = null;
    private String vFlag = null;
    private String availability = null;
   // private int quantity = 0;

    public String getDishImageUrl()
    {
        return imageURL;
    }
    public String getDishName()
    {
        return Name;
    }
    public String getDishPrice()
    {
        return price;
    }
    public String getDishVFlag()
    {
        return vFlag;
    }
    public String getDishAvailability()
    {
        return availability;
    }
    //public int getQuantity(){ return  quantity; }
    public void setDishImageUrl(String imageURL) { this.imageURL = imageURL;}
    public void setDishName(String Name)
    {
        this.Name = Name;
    }
    public void setDishPrice(String price)
    {
        this.price = price;
    }
    public void setDishVFlag(String vFlag)
    {
        this.vFlag = vFlag;
    }
    public void setDishAvailability(String availability)
    {
        this.availability = availability;
    }
   // public void setQuantity(int quantity){ this.quantity = quantity; }

}
