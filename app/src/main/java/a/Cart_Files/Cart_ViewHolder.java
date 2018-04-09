package a.Cart_Files;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import a.dot7.R;

/**
 * Created by TUSHAR on 09-04-18.
 */

public class Cart_ViewHolder extends RecyclerView.ViewHolder {

    private ArrayList<DishesDetails> dishesDetails;
    Context context;
    public Cart_ViewHolder(View itemView,Context context,RecyclerView CartResturantView,RecyclerView.LayoutManager  dishLayout) {
        super(itemView);
        this.context = context;
        setRecyclerViewDetails(itemView,CartResturantView,dishLayout);

    }
    private void setRecyclerViewDetails(View itemView,RecyclerView CartResturantView,RecyclerView.LayoutManager  dishLayout)
    {
       /* CartResturantView = itemView.findViewById();
        CartResturantView.setHasFixedSize(true);
        dishLayout = new LinearLayoutManager(context);
        CartResturantView.setLayoutManager(dishLayout);
       */
    }
}
