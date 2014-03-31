package com.example.asamles.app.sms;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.asamles.app.R;


public class SMSMain extends Fragment {
    private Button btn, btn2;
    private EditText phone, text;
    private String myPhoneNo = "+79231240849";

    public static SMSMain newInstance() {
        SMSMain fragment = new SMSMain();
        return fragment;
    }

    public SMSMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sms_main, container, false);

        btn = (Button) rootView.findViewById(R.id.send);
        btn2 = (Button) rootView.findViewById(R.id.send_intent);
        phone = (EditText) rootView.findViewById(R.id.editTextPhoneNo);
        text = (EditText) rootView.findViewById(R.id.editTextText);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                btn.setText("working!");
                String phoneNo = phone.getText().toString();
                String smstext = text.getText().toString();
                if (phoneNo != null && phoneNo.length() > 0) {
                    SMSSend.sendSMS(phoneNo, smstext);
                } else {
                    SMSSend.sendSMS(myPhoneNo, smstext);
                }
                btn.setText("Send!");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                btn.setText("working!");
                String phoneNo = phone.getText().toString();
                String smstext = text.getText().toString();
                if (phoneNo != null && phoneNo.length() > 0) {
                    SMSSend.sendSMSIntent(phoneNo, smstext, getActivity());
                } else {
                    SMSSend.sendSMSIntent(myPhoneNo, smstext, getActivity());
                }
                btn.setText("Send!");
            }
        });

        return rootView;
    }

    // private void sendSMS(String phoneNumber, String message)
    // {
    // if(phoneNumber != null && phoneNumber.length()>0) {
    // SmsManager sms = SmsManager.getDefault();
    // if (message != null && message.length()>0) {
    // sms.sendTextMessage(phoneNumber, null, message, null, null);
    // } else { return;}
    // }
    // }
    // private void sendSMSIntent(String phoneNumber, String message) {
    // if(phoneNumber != null && phoneNumber.length()>0) {
    // Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber) );
    // if (message != null && message.length()>0) {
    // intent.putExtra( "sms_body", message );
    // startActivity( intent );
    // } else { return;}
    // }
    // }
    @Override
    public void onResume() {
        super.onResume();

    }

}