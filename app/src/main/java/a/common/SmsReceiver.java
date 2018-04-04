package a.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by TUSHAR on 28-03-18.
 */

public class SmsReceiver extends BroadcastReceiver {
    private static SmsListener mListener;
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data  = intent.getExtras();
        Log.d("HAR", "message ke baad call hua hai");
        Object[] pdus = (Object[]) data.get("pdus");

        for(int i=0;i<pdus.length;i++){
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);

            String Sender = smsMessage.getDisplayOriginatingAddress();

            String messageBody = smsMessage.getMessageBody();
            Log.d("HAR", "message is:"+messageBody);
            if (Sender.contentEquals("IM-DOTSMS")) {
                Log.d("HAR", "message detected successfully");
                mListener.messageReceived(messageBody);
            }

        }


    }
    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }
}
