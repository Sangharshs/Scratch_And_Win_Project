package com.cashii;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_DATE_CHANGED)) {
            Toast.makeText(context, "Date Changed Makda", Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "Date Changed Makda", Toast.LENGTH_LONG).show();
        }

    }
}