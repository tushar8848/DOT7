package a.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsMessage;

/**
 * Created by Suman on 14-03-2018.
 */

public class SMS_broadcastReciever extends BroadcastReceiver {

    private static Broadcast_Listener mlistener;

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle=intent.getExtras();
        SmsMessage sms;
        String sms_str;

        if(bundle != null)
        {
            Object[] pdus = (Object[]) bundle.get("pdus");
            for (int i = 0 ; i < pdus.length ; i++)
            {
                sms=SmsMessage.createFromPdu((byte[]) pdus[i]);
                sms_str=sms.getMessageBody().toString();
                String Sender = sms.getDisplayOriginatingAddress();
                Intent smsIntent = new Intent("otp");
                smsIntent.putExtra("message",sms_str);
                if (Sender.endsWith(""))                // Enter last part to detect the sender
                    mlistener.message_Received(sms_str,smsIntent);
            }
        }
    }

    public static void bindListener(Broadcast_Listener listener)
    {
        mlistener=listener;
    }
}
