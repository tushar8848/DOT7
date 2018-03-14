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

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle=intent.getExtras();
        SmsMessage[] sms;
        String sms_str;

        if(bundle!=null)
        {
            Object[] pdus= (Object[]) bundle.get("pdus");
            sms=new SmsMessage[pdus.length];
            for (int i=0;i<sms.length;i++)
            {
                sms[i]=SmsMessage.createFromPdu((byte[]) pdus[i]);
                sms_str=sms[i].getMessageBody().toString();
                String Sender = sms[i].getOriginatingAddress();
                Intent smsIntent = new Intent("otp");
                smsIntent.putExtra("message",sms_str);
                LocalBroadcastManager.getInstance(context).sendBroadcast(smsIntent);
            }
        }
    }
}
