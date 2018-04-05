package a.home_screen;

/**
 * Created by TUSHAR on 02-04-18.
 */

import android.content.Context;
import android.media.Image;
import android.support.design.widget.CheckableImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import a.common.GlideApp;
import a.dot7.R;
import a.getter_setter.Restaurant_Each_Row_data;

public class RestaurantView_Adapter extends RecyclerView.Adapter<RestaurantView_Adapter.ViewHolder> {

    private final Context context;
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
        if(RowData.isShowShimmer()){
            holder.shimmerFrameLayout.startShimmerAnimation();
        }
        else {
            holder.shimmerFrameLayout.stopShimmerAnimation();
            holder.Restro_Name.setBackground(null);
            holder.Cuisine.setBackground(null);
            holder.timing.setBackground(null);
            holder.Rating.setBackground(null);

            holder.Rating.setText(RowData.getRestaurantRating());
            holder.Cuisine.setText(RowData.getRestaurantCuisine());
            holder.Restro_Name.setText(RowData.getRestaurantName());
            holder.timing.setText(RowData.getRestaurantTiming());
        }
        String favourite_flag = RowData.getRestaurantFavflag();
        if(favourite_flag.equals("1"))
            holder.Favourite_Flag.setVisibility(View.VISIBLE);
        //  holder.Favourite_Flag.setChecked(favourite_flag);

        //holder.Restro_Image.setImageResource(R.drawable.ic_launcher_background);
       /* Picasso.get().
                load(RowData.
                getRestaurantImage()).resize(300,300).
               into(holder.Restro_Image);*/


        GlideApp
                .with(context)
                .load(RowData.getRestaurantImage())
                .centerCrop()
                .placeholder(R.drawable.dot7_low)
                .into(holder.Restro_Image);
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
        ImageView Favourite_Flag;
        public ShimmerFrameLayout shimmerFrameLayout;
        private ViewHolder(View itemView) {
            super(itemView);
            shimmerFrameLayout = itemView.findViewById(R.id.fl_shimmer);
            timing = itemView.findViewById( R.id.timing);
            Restro_Name = itemView.findViewById( R.id.res_name);
            Cuisine = itemView.findViewById( R.id.cuisines_list);
            Rating = itemView.findViewById( R.id.Rating);
            Restro_Image = itemView.findViewById(R.id.res_image);
            Favourite_Flag = itemView.findViewById(R.id.favourite);
        }
    }
}
