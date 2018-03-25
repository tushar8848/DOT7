package a.common;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suman on 15-03-2018.
 */

public class Permissions {

    private static Permissions permissions;
    private Context context;

    private Permissions(Context context)
    {
        this.context=context;
    }

    public static synchronized Permissions getInstance(Context context)
    {
        if (permissions == null)
            permissions = new Permissions(context);
        return permissions;
    }

    public boolean checkpermissions(Context context, int REQUEST_ID_MULTIPLE_PERMISSIONS) {

        int receiveSMS = ContextCompat.checkSelfPermission(context, Manifest.permission.RECEIVE_SMS);
        int readSMS = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_MMS);
        }

        if (readSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) context,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
}
