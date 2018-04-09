package a.Cart_Files;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import a.dot7.R;

/**
 * Created by TUSHAR on 09-04-18.
 */

public class CartAdapter extends RecyclerView.Adapter<Cart_ViewHolder> {

    ArrayList<IndividualRestaurant> Restaurants;
    Context context;
    CartAdapter(Context context,ArrayList<IndividualRestaurant> restaurants)
    {
        this.context = context;
        this.Restaurants = restaurants;
    }


    @Override
    public Cart_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(Cart_ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
