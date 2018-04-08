package a.Lifecycle_Restaurant_Ordering;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import a.common.GlideApp;
import a.dot7.R;
import a.getter_setter.Dishes;

/**
 * Created by TUSHAR on 07-04-18.
 */

public class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.DishViewHolder> {
    private final Context context;
    private List<Dishes> data;
    public DishesAdapter(Context context,List<Dishes> data)
    {
        this.context = context;
        this.data = data;
    }

    @Override
    public DishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //***********************************************replace single item dish view*******************************************************
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dish, parent, false);

        DishViewHolder viewHolder = new DishViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DishesAdapter.DishViewHolder holder, int position) {

        Dishes BlockData = data.get(position);
        holder.DishName.setText(BlockData.getDishName());
        holder.DishPrice.setText(BlockData.getDishPrice());
        String vFlag = BlockData.getDishVFlag();
       /* if(vFlag.equals("1"))
            holder.DishVegImage.setVisibility(View.VISIBLE);
        else
            holder.DishNVegImage.setVisibility(View.VISIBLE);
*/
       holder.DishVegImage.setVisibility(View.VISIBLE);
        GlideApp.with(context)
                .load(BlockData.getDishImageUrl())
                .centerCrop()
                .into(holder.DishImage);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public class DishViewHolder extends RecyclerView.ViewHolder {
        TextView DishName;
        TextView DishPrice;
        ImageView DishVegImage;
        ImageView DishNVegImage;
        ImageView DishImage;
        TextView Quantity;
        Button DishAdd;
        Button QMinus;
        Button QPlus;

        public DishViewHolder(View itemView) {
            super(itemView);
            //*******************************************set id's*************************************************************

          DishName = itemView.findViewById(R.id.dish_name);
            DishPrice = itemView.findViewById(R.id.dish_price);
            DishVegImage = itemView.findViewById(R.id.veg_non_veg_logo);
            DishImage = itemView.findViewById(R.id.dish_thumbnail);
           // DishNVegImage = itemView.findViewById();
          //  Quantity =  itemView.findViewById(R.id.count);
          //  DishAdd = itemView.findViewById();
            //QMinus =  itemView.findViewById();
            //QPlus =  itemView.findViewById();

       /*  DishAdd.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(mListener!=null){
                     int position = getAdapterPosition();
                     if(position != RecyclerView.NO_POSITION){
                         mListener.onItemClick(position);
                     }
                 }
             }
         });
         QMinus.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(mListener!=null){
                     int position = getAdapterPosition();
                     if(position != RecyclerView.NO_POSITION){
                         mListener.onItemClick(position);
                     }
                 }
             }
         });
         QPlus.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(mListener!=null){
                     int position = getAdapterPosition();
                     if(position != RecyclerView.NO_POSITION){
                         mListener.onItemClick(position);
                     }
                 }
             }
         });*/
        }
    }
}
