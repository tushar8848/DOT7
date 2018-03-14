package a.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class MyDialog {

    private String Msg=null;
    private AlertDialog CustomDialog;
    private Context context;
    private String ButtonText;

    public MyDialog(Context context, String msg,String text)
    {
        this.context=context;
        this.Msg=msg;
        this.ButtonText = text;
        onCreateDialog();
    }

    public void onCreateDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage(Msg);
        builder.setPositiveButton(ButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        CustomDialog=builder.create();
        CustomDialog.show();
    }
}
