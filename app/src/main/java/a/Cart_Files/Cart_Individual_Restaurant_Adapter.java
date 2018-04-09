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

class Cart_Individual_Restaurant_Adapter extends RecyclerView.Adapter<CartRestaurant_ViewHolder>{
    ArrayList<DishesDetails> dishesDetails;
    Context context;
    Cart_Individual_Restaurant_Adapter(Context context,ArrayList<DishesDetails> dishesDetails)
    {
        this.context = context;
        this.dishesDetails = dishesDetails;
    }
    @Override
    public CartRestaurant_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item_restaurant_view, parent, false);

        CartRestaurant_ViewHolder viewHolder = new CartRestaurant_ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CartRestaurant_ViewHolder holder, int position) {
        DishesDetails dishes = dishesDetails.get(position);
        holder.DName.setText(dishes.getDishName());
        holder.Dprice.setText(dishes.getDishPrice());
        holder.DQuantity.setText(dishes.getQuantity());

    }

    @Override
    public int getItemCount() {
        return dishesDetails.size();
    }
}
