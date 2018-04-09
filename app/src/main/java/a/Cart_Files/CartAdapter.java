package a.Cart_Files;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import a.dot7.R;
import a.getter_setter.Dishes;

/**
 * Created by TUSHAR on 09-04-18.
 */

public class CartAdapter extends RecyclerView.Adapter<Cart_ViewHolder> {

    ArrayList<IndividualRestaurant> Restaurants;
    Context context;
    private Cart_Individual_Restaurant_Adapter cart_individual_restaurant_adapter;
    CartAdapter(Context context,ArrayList<IndividualRestaurant> restaurants)
    {
        this.context = context;
        this.Restaurants = restaurants;
    }


    @Override
    public Cart_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item_restaurant_view, parent, false);

        Cart_ViewHolder viewHolder = new Cart_ViewHolder(v,context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Cart_ViewHolder holder, int position) {
        IndividualRestaurant individualRestaurant = Restaurants.get(position);
        holder.RName.setText(individualRestaurant.getRName());
        ArrayList<DishesDetails> dishes = individualRestaurant.getRDishes();
        cart_individual_restaurant_adapter = new Cart_Individual_Restaurant_Adapter(context,dishes);
        holder.CartResturantView.setAdapter(cart_individual_restaurant_adapter);
    }

    @Override
    public int getItemCount() {
        return Restaurants.size();
    }
}
