package buttertext.com.buttertext;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";


    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();

        // Set up a Message and a Contact

        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                String smsBody = smsMessage.getMessageBody().toString();
                String address = smsMessage.getOriginatingAddress();

                //Check DB to see if address exists:
                    //If NO
                        //1. Set up Contact
                        //2. Insert Contact

                //1. Set up Message
                //2. Insert into DB
                //3. Create a CONTACT_MESSAGE
                //4. Insert CONTACT_MESSAGE

                smsMessageStr += "SMS From: " + address + "\n";
                smsMessageStr += smsBody + "\n";
            }
            Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT).show();

            //this will update the UI with message
            ConversationActivity inst = ConversationActivity.instance();
            inst.updateList(smsMessageStr);
        }
    }
}