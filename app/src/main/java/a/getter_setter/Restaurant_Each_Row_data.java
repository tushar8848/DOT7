package a.getter_setter;

/**
 * Created by TUSHAR on 02-04-18.
 */


public class Restaurant_Each_Row_data {

    private String timing=null;
    private String imageURL=null;
    private String restaurantName=null;
    private String cuisines=null;
    private String rating=null;
    private String isFavourite=null;

    public String getRestaurantTiming()
    {
        return timing;
    }
    public String getRestaurantImage()
    {
        return imageURL;
    }

    public String getRestaurantName()
    {
        return restaurantName;
    }

    public String getRestaurantCuisine()
    {
        return cuisines;
    }

    public String getRestaurantRating()
    {
        return rating;
    }

    public String getRestaurantFavflag()
    {
        return isFavourite;
    }




    public void setRestaurantTiming(String timing)
    {
        this.timing = timing;
    }

    public void setRestaurantImage(String Image)
    {
        this.imageURL = Image;
    }

    public void setRestaurantName(String Name)
    {
        this.restaurantName = Name;
    }

    public void setRestaurantCuisine(String Cuisine)
    {
        int l = Cuisine.length();
        this.cuisines = Cuisine.substring(0,l-1);
    }

    public void setRestaurantRating(String Rating)
    {
        this.rating = String.valueOf(Rating);
    }

    public void setRestaurantFavflag(String Flag)
    {
        this.isFavourite=Flag;
    }
}
