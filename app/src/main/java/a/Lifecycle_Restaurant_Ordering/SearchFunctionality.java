package a.Lifecycle_Restaurant_Ordering;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;

import a.dot7.R;
import a.getter_setter.Restaurants;

/**
 * Created by Suman on 09-04-2018.
 */

public class SearchFunctionality extends Fragment {

    static SearchFunctionality Search = null;
    private List<String> Restaurants_name;
    private ArrayAdapter<String> Adapter;
    private Context context;
    ListView SearchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment,container,false);
    }

    public void StartSearch(List<Restaurants> AllRowData) {

        Log.e("AA gye","wohoo");
        //Restaurants_name = new String[AllRowData.size()];
        Restaurants RowData;
        this.context = context;

        for (int i = 0; i < AllRowData.size(); i++)
        {
            RowData = AllRowData.get(i);
            Restaurants_name.add(RowData.getRestaurantName());
            Log.e("Added Restaurant name: ",RowData.getRestaurantName());
        }

        Fill_list();
        /*SearchButton.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               // SearchFunctionality.this.Adapter.getFilter().filter(charSequence);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SearchFunctionality.this.Adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/

    }

    private void Fill_list() {
        //SearchView = find



        Log.e("list fill ho gyi","");
        Adapter = new ArrayAdapter<>(context, R.layout.search_list_item,R.id.text,Restaurants_name);
        SearchView.setAdapter(Adapter);
    }


    public static SearchFunctionality getInstance() {
        if (Search == null)
            Search = new SearchFunctionality();
        return Search;
    }
}
