package a.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class MyDialog {

    String msg=null;
    AlertDialog dialog;
    Context context;

    public MyDialog(Context context, String msg)
    {
        this.context=context;
        this.msg=msg;
        onCreateDialog();
    }

    public void onCreateDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        dialog=builder.create();
        dialog.show();
    }
}
