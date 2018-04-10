package a.Lifecycle_Restaurant_Ordering;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import a.common.GlobalMethods;
import a.dot7.R;

/**
 * Created by Suman on 09-04-2018.
 */

public class SearchableActivity extends ListActivity {

    SearchView searchView;
    Intent intent;
    private ArrayList<String> Search_Result = null;
    private String Enetered_query;
    private Bundle bundle;
    ArrayList<String> Restaurants_Name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.);

        Log.e("","searchable activity created");
        intent = getIntent();
        Log.e("","Intent le liya");
        handleIntent(intent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView)  menu.findItem(R.id.Search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        Log.e("","Searchable info created along with other components");
        return true;
    }

    public void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        // call detail activity for clicked entry
    }

    private void handleIntent(Intent intent) {
        if(Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            String Query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(Query);

            Log.e("Query Dispatched: ",Query);
        }
        bundle = intent.getBundleExtra("Restaurants");
        Restaurants_Name = bundle.getStringArrayList("Names");

        Log.e("Got Restaurant Nmaes","cool");
    }

    private void doSearch(String queryStr) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                AlertDialog alertDialog = new AlertDialog.Builder(SearchableActivity.this).create();
                alertDialog.setMessage("Search keyword is " + query);
                alertDialog.show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                GlobalMethods.print(SearchableActivity.this,newText);
                return false;
            }
        });

    }

}
