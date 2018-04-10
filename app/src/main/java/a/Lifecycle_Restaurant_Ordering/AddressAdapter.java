package a.Lifecycle_Restaurant_Ordering;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import a.common.GlideApp;
import a.dot7.R;
import a.getter_setter.Dishes;
import a.getter_setter.Restaurants;
import a.getter_setter.User_Address;

/**
 * Created by HP on 10-04-2018.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder>{
    private final Context context;
    private List<User_Address> data;

    public AddressAdapter(Context context, List<User_Address> data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item_restaurant_view, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        User_Address RowData = data.get(position);

            holder.FlatNo.setText(RowData.getFlatNo());
            holder.StreetName.setText(RowData.getStreetName());
            holder.LandMark.setText(RowData.getLandMark());
            holder.State.setText(RowData.getState());
            holder.District.setText(RowData.getDistrict());
            holder.Pincode.setText(RowData.getPincode());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    private RestaurantsAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(RestaurantsAdapter.OnItemClickListener listener){
        mListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView FlatNo;
        TextView StreetName;
        TextView LandMark;
        TextView State;
        TextView District;
        TextView Pincode;
        //public ShimmerFrameLayout shimmerFrameLayout;

        private ViewHolder(View itemView) {
            super(itemView);
            //shimmerFrameLayout = itemView.findViewById(R.id.fl_shimmer);
            FlatNo = itemView.findViewById( R.id.timing);
            StreetName = itemView.findViewById( R.id.res_name);
            LandMark = itemView.findViewById( R.id.cuisines_list);
            State = itemView.findViewById( R.id.cuisines_list);
            Pincode = itemView.findViewById( R.id.cuisines_list);
            District = itemView.findViewById(R.id.cuisines_list);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener!=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
