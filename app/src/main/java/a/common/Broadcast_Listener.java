package a.common;


import android.content.Intent;

/**
 * Created by Suman on 18-03-2018.
 */

public interface Broadcast_Listener {

    public void message_Received(String message, Intent smsIntent);

}
