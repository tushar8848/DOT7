package a.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class Load_ImageTask extends AsyncTask<String,Void,Bitmap> {

    ImageView Image;

    public Load_ImageTask(ImageView imgView)
    {
        Image = imgView;
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
