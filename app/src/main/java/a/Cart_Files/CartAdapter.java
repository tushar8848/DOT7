package a.Cart_Files;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import a.dot7.R;

/**
 * Created by TUSHAR on 09-04-18.
 */

public class CartAdapter extends RecyclerView.Adapter<Cart_ViewHolder> {

    ArrayList<IndividualRestaurantData> Restaurants;
    Context context;
    private Cart_Individual_Restaurant_Adapter cart_individual_restaurant_adapter;
    CartAdapter(Context context,ArrayList<IndividualRestaurantData> restaurants)
    {
        this.context = context;
        this.Restaurants = restaurants;
    }


    @Override
    public Cart_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);

        Cart_ViewHolder viewHolder = new Cart_ViewHolder(v,context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Cart_ViewHolder holder, int position) {
        IndividualRestaurantData individualRestaurantData = Restaurants.get(position);
        holder.RName.setText(individualRestaurantData.getRName());
        ArrayList<DishesData> dishes = individualRestaurantData.getRDishes();
        cart_individual_restaurant_adapter = new Cart_Individual_Restaurant_Adapter(context,dishes);
        holder.CartResturantView.setAdapter(cart_individual_restaurant_adapter);
    }

    @Override
    public int getItemCount() {
        return Restaurants.size();
    }
}
