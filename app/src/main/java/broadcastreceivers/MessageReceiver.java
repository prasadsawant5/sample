package broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsMessage;
import android.util.Log;

import constants.ApplicationConstants;

/**
 * Created by prasadsawant on 11/11/16.
 */

public class MessageReceiver extends BroadcastReceiver {

    private static final String TAG = MessageReceiver.class.getName();

    private String messageBody;
    private String token;

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(ApplicationConstants.ACTION_SMS_RECEIVED)) {
            Bundle pdusBundle = intent.getExtras();
            Object[] pdus = (Object[]) pdusBundle.get(ApplicationConstants.PDUS);
            SmsMessage[] smsMessage = new SmsMessage[pdus.length];

            for (int i = 0; i < smsMessage.length; i++) {
                smsMessage[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

                messageBody = smsMessage[i].getMessageBody();
            }

            token = messageBody.substring(messageBody.indexOf(":") + 2, messageBody.length());

            Intent tokenIntent = new Intent();
            tokenIntent.setAction(ApplicationConstants.ACTION_TOKEN_RECEIVED);
            tokenIntent.putExtra(ApplicationConstants.EXTRA_TOKEN, token);
            LocalBroadcastManager.getInstance(context).sendBroadcast(tokenIntent);

            abortBroadcast();
        }
    }
}
