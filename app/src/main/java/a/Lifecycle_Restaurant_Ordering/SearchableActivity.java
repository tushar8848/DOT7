package a.Lifecycle_Restaurant_Ordering;

import android.app.Activity;
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

import a.dot7.R;

/**
 * Created by Suman on 09-04-2018.
 */

public class SearchableActivity extends ListActivity {

    SearchView searchView;
    Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.);

    Log.e("","searchable activity created");
        intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            String Query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(Query);
        }

    }

    private void searchViewListeners() {

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                return false;
            }
        });
    }

    private void doSearch(String query) {



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.Search).getActionView();
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        Log.e("","Searchable info created along with other components");
        return true;
    }
}
