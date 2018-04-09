package a.Cart_Files;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import a.dot7.R;

/**
 * Created by TUSHAR on 09-04-18.
 */

public class Cart_ViewHolder extends RecyclerView.ViewHolder {

    RecyclerView CartResturantView;
    RecyclerView.LayoutManager  dishLayout;
    TextView RName;
    Context context;
    public Cart_ViewHolder(View itemView,Context context) {
        super(itemView);
        this.context = context;
        setRecyclerViewDetails(itemView);

    }
    private void setRecyclerViewDetails(View itemView)
    {
        CartResturantView = itemView.findViewById(R.id.dish_list);
        CartResturantView.setHasFixedSize(true);
        dishLayout = new LinearLayoutManager(context);
        CartResturantView.setLayoutManager(dishLayout);
        RName = itemView.findViewById(R.id.CartRestaurant_Name);

    }
}
