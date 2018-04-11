package a.dot7;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import a.getter_setter.DeveloperDetails;

public class DevDummyFragment extends Fragment {
    TextView Name, Title;
    ImageView imageView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_nitish, container, false);
        Name = view.findViewById(R.id.dev_name);
        Title = view.findViewById(R.id.devTitle);
        imageView = view.findViewById(R.id.dev_thumbnail);
        return view;

    }

}
