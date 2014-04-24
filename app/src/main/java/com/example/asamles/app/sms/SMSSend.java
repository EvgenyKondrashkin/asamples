package com.example.asamles.app.sms;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;

public class SMSSend {

    public static void sendSMS(String phoneNumber, String message) {
        if (phoneNumber != null && phoneNumber.length() > 0) {
            SmsManager sms = SmsManager.getDefault();
            if (message != null && message.length() > 0) {
                sms.sendTextMessage(phoneNumber, null, message, null, null);
            }
        }
    }

    public static void sendSMSIntent(String phoneNumber, String message, Context context) {
        if (phoneNumber != null && phoneNumber.length() > 0) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
            if (message != null && message.length() > 0) {
                intent.putExtra("sms_body", message);
                context.startActivity(intent);
            }
        }
    }
}