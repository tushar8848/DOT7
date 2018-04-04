package a.home_screen;

/**
 * Created by TUSHAR on 02-04-18.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import a.dot7.R;

public class Load_ImageTask extends AsyncTask<String,Void,Void> {

    private final ImageView Image;
    private final Context context;

    public Load_ImageTask(ImageView imgView, Context context)
    {
        Image = imgView;
        this.context = context;
    }
    @Override
    protected Void doInBackground(String... urls) {

        String UrlDisplay = urls[0];
        try
        {
            Picasso.with(context).load(UrlDisplay)
                    .placeholder(R.drawable.ic_menu_white_18dp)
                    .error(R.drawable.ic_menu_white_18dp)
                    .into(Image);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}

/* *****************************************************************************
    String UrlDisplay = urls[0];
    Bitmap bitmap = null;
    InputStream in =  new java.net.URL(UrlDisplay).openStream();
            bitmap = BitmapFactory.decodeStream(in);
    return bitmap;

 */