package a.home_screen;

/**
 * Created by TUSHAR on 02-04-18.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class Load_ImageTask extends AsyncTask<String,Void,Bitmap> {

    private ImageView Image;
    private Context context;

    public Load_ImageTask(ImageView imgView, Context context)
    {
        Image = imgView;
        this.context = context;
    }
    @Override
    protected Bitmap doInBackground(String... urls) {
        String UrlDisplay = urls[0];
        Bitmap bitmap = null;
        try
        {
            InputStream in =  new java.net.URL(UrlDisplay).openStream();
            bitmap = BitmapFactory.decodeStream(in);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        Image.setImageBitmap(bitmap);
    }
}
