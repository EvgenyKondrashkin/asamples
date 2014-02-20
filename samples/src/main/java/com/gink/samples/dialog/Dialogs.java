package com.gink.samples.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gink.samples.R;

import java.util.Calendar;

public class Dialogs extends Activity {
    private Button btn, btn2, btn3;
    private TextView label, label2, label3;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        context = this;
        btn = (Button) findViewById(R.id.time);
        btn2 = (Button) findViewById(R.id.button);
        btn3 = (Button) findViewById(R.id.button2);
        label = (TextView) findViewById(R.id.textView);
        label2 = (TextView) findViewById(R.id.textView2);
        label3 = (TextView) findViewById(R.id.textView3);

        label2.setText(""+ System.currentTimeMillis());
        Calendar date = Calendar.getInstance();
        label3.setText(""+ date.getTimeInMillis());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                btn.setText("working!");
                ADialogs.openTime(context, label);

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                btn2.setText("working!");
                ADialogs.alert("Wat", context);

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                btn3.setText("working!");
                Calendar date1 = Calendar.getInstance();
                date1.set(Calendar.HOUR_OF_DAY, 0);
                date1.set(Calendar.MINUTE, 0);
                date1.set(Calendar.SECOND, 0);
                date1.set(Calendar.MILLISECOND, 0);
                label3.setText(""+ date1.getTimeInMillis());
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}