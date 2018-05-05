

package com.TVCastLib.device;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * ###Overview
 * The DevicePicker is provided by the DiscoveryManager as a simple way for you to present a list of available devices to your users.
 *
 * ###In Depth
 * By calling the getPickerDialog you will get a reference to the AlertDialog that will be updated automatically updated as compatible devices are discovered.
 */
public class DevicePicker {
    Activity activity;
    ConnectableDevice device;

    /**
     * Creates a new DevicePicker
     * 
     * @param activity Activity that DevicePicker will appear in
     */
    public DevicePicker(Activity activity) {
        this.activity = activity;
    }

    public ListView getListView() {
        return new DevicePickerListView(activity);
    }

    /**
     * Sets a selected device.
     * 
     * @param device Device that has been selected.
     */
    public void pickDevice(ConnectableDevice device) {
        this.device = device;
    }

    /**
     * Cancels pairing with the currently selected device.
     */
    public void cancelPicker() {
        if (device != null) {
            device.cancelPairing();
        }
        device = null;
    }

    /**
     * This method will return an AlertDialog that contains a ListView with an item for each discovered ConnectableDevice.
     *
     * @param message The title for the AlertDialog
     * @param listener The listener for the ListView to get the item that was clicked on
     */
    public AlertDialog getPickerDialog(String message, final OnItemClickListener listener) {
        final DevicePickerListView view = new DevicePickerListView(activity);

        TextView title = (TextView) activity.getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null);
        title.setText(message);

        final AlertDialog pickerDialog = new AlertDialog.Builder(activity)
        .setCustomTitle(title)
        .setCancelable(true)
        .setView(view)
        .create();

        view.setOnItemClickListener(new OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
                listener.onItemClick(arg0, arg1, arg2, arg3);
                pickerDialog.dismiss();
            }
        });

        return pickerDialog;
    }
}
