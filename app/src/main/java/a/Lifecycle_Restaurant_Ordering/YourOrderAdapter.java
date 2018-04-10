package a.Lifecycle_Restaurant_Ordering;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import a.dot7.R;
import a.getter_setter.Cart;
import a.getter_setter.RestaurantOrders;

/**
 * Created by TUSHAR on 10-04-18.
 */

public class YourOrderAdapter extends RecyclerView.Adapter<Youroder_ViewHolder> {
    ArrayList<RestaurantOrders> orders;
    Context context;
    YourOrderAdapter(Context context,ArrayList<RestaurantOrders> orders)
    {
        this.context = context;
        this.orders = orders;
    }
    @Override
    public Youroder_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        Youroder_ViewHolder holder = new Youroder_ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(Youroder_ViewHolder holder, int position) {

        RestaurantOrders data = orders.get(position);
        holder.RName.setText(data.getRName());
        holder.OrderID.setText(data.getOrderId());
        holder.OrderDate.setText(data.getOrderDate());
        holder.TotalPrice.setText(data.getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
class Youroder_ViewHolder extends RecyclerView.ViewHolder {
    TextView RName;
    TextView TotalPrice;
    TextView OrderDate;
    TextView OrderID;
    public Youroder_ViewHolder(View itemView) {
        super(itemView);

        RName = itemView.findViewById(R.id.YourOrderRestaurant_Name);
        TotalPrice = itemView.findViewById(R.id.OrderTotal_price);
        OrderDate = itemView.findViewById(R.id.OrderDate);
        OrderID = itemView.findViewById(R.id.order_id);

    }
}
