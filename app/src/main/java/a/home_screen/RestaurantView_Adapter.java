package a.home_screen;

/**
 * Created by TUSHAR on 02-04-18.
 */

import android.content.Context;
import android.support.design.widget.CheckableImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import a.dot7.R;
import a.getter_setter.Restaurant_Each_Row_data;

public class RestaurantView_Adapter extends RecyclerView.Adapter<RestaurantView_Adapter.ViewHolder> {

    private Context context;
    private List<Restaurant_Each_Row_data> data;

    public RestaurantView_Adapter(Context context, List<Restaurant_Each_Row_data> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item_restaurant_view, parent, false);
       ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Restaurant_Each_Row_data RowData = data.get(position);

        holder.Rating.setText(RowData.getRestaurantRating());
        holder.Cuisine.setText(RowData.getRestaurantCuisine());
        holder.Restro_Name.setText(RowData.getRestaurantName());
        holder.timing.setText(RowData.getRestaurantTiming());

        String favourite_flag = RowData.getRestaurantFavflag();
        //  holder.Favourite_Flag.setChecked(favourite_flag);

        holder.Restro_Image.setImageResource(R.drawable.ic_launcher_background);
        new Load_ImageTask(holder.Restro_Image, context).execute(RowData.getRestaurantImage());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView timing;
        TextView Cuisine;
        TextView Restro_Name;
        ImageView Restro_Image;
        TextView Rating;
        CheckableImageButton Favourite_Flag;

        private ViewHolder(View itemView) {
            super(itemView);
            timing = itemView.findViewById( R.id.timing);
            Restro_Name = itemView.findViewById( R.id.res_name);
            Cuisine = itemView.findViewById( R.id.cuisines_list);
            Rating = itemView.findViewById( R.id.Rating);
            Restro_Image = itemView.findViewById(R.id.res_image);
            Favourite_Flag = itemView.findViewById(R.id.favourite);
        }
    }
}
