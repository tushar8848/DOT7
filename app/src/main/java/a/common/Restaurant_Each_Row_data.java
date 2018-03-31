package a.common;

import android.media.Rating;

public class Restaurant_Each_Row_data {

    private String RestaurantTiming;
    private String RestaurantImage;
    private String RestaurantName;
    private String RestaurantCuisine;
    private String RestaurantRating;
    private boolean Restaurant_Favourite_Flag = false;

    public String getRestaurantTiming()
    {
        return RestaurantTiming;
    }
    public String getRestaurantImage()
    {
        return RestaurantImage;
    }

    public String getRestaurantName()
    {
        return RestaurantName;
    }

    public String getRestaurantCuisine()
    {
        return RestaurantCuisine;
    }

    public String getRestaurantRating()
    {
        return RestaurantRating;
    }

    public boolean getRestaurantFavflag()
    {
        return Restaurant_Favourite_Flag;
    }

    public void setRestaurantTiming(String timing)
    {
        this.RestaurantTiming = timing;
    }
    public void setRestaurantImage(String Image)
    {
        this.RestaurantImage = Image;
    }

    public void setRestaurantName(String Name)
    {
        this.RestaurantName = Name;
    }

    public void setRestaurantCuisine(String Cuisine)
    {
        this.RestaurantCuisine = Cuisine;
    }

    public void setRestaurantRating(String Rating)
    {
        this.RestaurantRating = String.valueOf(Rating);
    }

    public void setRestaurantFavflag(boolean Flag)
    {
        this.Restaurant_Favourite_Flag=Flag;
    }
}
