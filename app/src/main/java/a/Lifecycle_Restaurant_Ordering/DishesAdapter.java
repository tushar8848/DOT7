package a.Lifecycle_Restaurant_Ordering;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import a.common.GlideApp;
import a.dot7.R;
import a.getter_setter.Cart;
import a.getter_setter.Dishes;

/**
 * Created by TUSHAR on 07-04-18.
 */

public class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.DishViewHolder> {
    private final Context context;
    private List<Dishes> data;
    private String RKey;
    private String RName;
    Dishes BlockData;
    int totalQuantity;
    Snackbar snackbar;
    View sbView;
    public DishesAdapter(Context context,List<Dishes> data,Snackbar snackbar,String Rkey,String Rname)
    {
        this.context = context;
        this.data = data;
        this.snackbar = snackbar;
        this.RKey = Rkey;
        this.RName = Rname;
    }

    @Override
    public DishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dish, parent, false);

        DishViewHolder viewHolder = new DishViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final DishesAdapter.DishViewHolder holder, final int position) {

       BlockData = data.get(position);
        holder.DishName.setText(BlockData.getDishName());
        holder.DishPrice.setText(BlockData.getDishPrice());
        String vFlag = BlockData.getDishVFlag();
        if(vFlag.equals("0"))
            holder.DishVegImage.setImageResource(R.drawable.non_veg_logo);
       holder.DishVegImage.setVisibility(View.VISIBLE);
        GlideApp.with(context)
                .load(BlockData.getDishImageUrl())
                .centerCrop()
                .into(holder.DishImage);
        holder.DishAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // if(mListener!=null)
                {
                  //  mListener.onItemClick(position);
                    int quantity = 1;
                    BlockData.setQuantity(quantity);

                    holder.DishAdd.setVisibility(View.GONE);
                    holder.Quantity.setText(String.valueOf(quantity));
                   holder.QuantityModifier.setVisibility(View.VISIBLE);


                    Cart_Items.getInstance(context).addDish(BlockData, RName, RKey);
                    totalQuantity = Cart_Items.getInstance(context).getTotalQuantity();


                   if(totalQuantity == 1) {
                       snackbar.show();
                   }
                   else
                   {
                       snackbar.setText(totalQuantity+ "Item(s)");
                   }
                }
            }
        });
        holder.QPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = BlockData.getQuantity();
                quantity++;
                BlockData.setQuantity(quantity);

                Cart_Items.getInstance(context).addDish(BlockData,RName,RKey);
                totalQuantity = Cart_Items.getInstance(context).getTotalQuantity();
                snackbar.setText(totalQuantity+" item(s)");
                holder.Quantity.setText(String.valueOf(quantity));
            }
        });
        holder.QMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = BlockData.getQuantity();
                quantity--;
                BlockData.setQuantity(quantity);
                Cart_Items.getInstance(context).removeDish(BlockData.getDishKey());
                totalQuantity = Cart_Items.getInstance(context).getTotalQuantity();

               holder.Quantity.setText(String.valueOf(quantity));
                snackbar.setText(totalQuantity+" item(s)");
                if(quantity == 0)
                {
                    holder.DishAdd.setVisibility(View.VISIBLE);
                    holder.QuantityModifier.setVisibility(View.GONE);

                }
                if(totalQuantity == 0)
                {
                    snackbar.dismiss();
                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public class DishViewHolder extends RecyclerView.ViewHolder {
        TextView DishName;
        TextView DishPrice;
        ImageView DishVegImage;
        ImageView DishNVegImage;
        ImageView DishImage;
        TextView Quantity;
        LinearLayout QuantityModifier;
        Button DishAdd;
        Button QMinus;
        Button QPlus;

        public DishViewHolder(View itemView) {
            super(itemView);

          DishName = itemView.findViewById(R.id.dish_name);
            DishPrice = itemView.findViewById(R.id.dish_price);
            DishVegImage = itemView.findViewById(R.id.veg_non_veg_logo);
            DishImage = itemView.findViewById(R.id.dish_thumbnail);
           // DishNVegImage = itemView.findViewById();
            Quantity =  itemView.findViewById(R.id.DishQuantity);
            DishAdd = itemView.findViewById(R.id.add_to_cart_button);
            QMinus =  itemView.findViewById(R.id.Minus);
            QPlus =  itemView.findViewById(R.id.Plus);
            QuantityModifier = itemView.findViewById(R.id.quantity_modifier);

        }
    }
}
