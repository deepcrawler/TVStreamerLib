/*
 * PairingDialog
 * TVCastLib
 * 
 * Copyright (c) 2014 Hamed Ghaderipour.
 * Created by Hamed Ghaderipour on 19 Jan 2014
 * 

 */

package com.TVCastLib.device;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TextView;

import com.TVCastLib.service.DeviceService;


public class PairingDialog {
    Activity activity;
    ConnectableDevice device;

    public PairingDialog(Activity activity, ConnectableDevice device) {
        this.activity = activity;
        this.device = device;
    }

    public AlertDialog getSimplePairingDialog(int titleResId, int messageResId) {
        return new AlertDialog.Builder(activity)
        .setTitle(titleResId)
        .setMessage(messageResId)
        .setPositiveButton(android.R.string.cancel, null)
        .create();
    }

    public AlertDialog getPairingDialog(int resId) {
        return getPairingDialog(activity.getString(resId));
    }

    public AlertDialog getPairingDialog(String message) {
        TextView title = (TextView) activity.getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null);
        title.setText(message);

        final EditText input = new EditText(activity);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        return new AlertDialog.Builder(activity)
        .setCustomTitle(title)
        .setView(input)
        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString().trim();
                for (DeviceService service : device.getServices())
                    service.sendPairingKey(value);
            }
        })
        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
                // pickerDialog.dismiss();
            }
        })
        .create();
    }
}
