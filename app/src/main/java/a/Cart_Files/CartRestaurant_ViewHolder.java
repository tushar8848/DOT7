package a.Cart_Files;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by TUSHAR on 10-04-18.
 */

public class CartRestaurant_ViewHolder extends RecyclerView.ViewHolder {
    TextView DName;
    TextView Dprice;
    TextView DQuantity;
    public CartRestaurant_ViewHolder(View itemView) {
        super(itemView);
        DName = itemView.findViewById();
        Dprice = itemView.findViewById();
        DQuantity = itemView.findViewById();
    }
}
