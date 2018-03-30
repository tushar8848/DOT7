package a.dot7;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class restaurant_recycler_view extends Activity {

    private RecyclerView my_recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_recycler_view);
        my_recycler_view=findViewById(R.id.rec_view);
    }
}
