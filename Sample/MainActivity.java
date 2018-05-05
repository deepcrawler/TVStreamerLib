

package com.TVStreamerLib.sampler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.TVStreamerLib.device.ConnectableDevice;
import com.TVStreamerLib.device.ConnectableDeviceListener;
import com.TVStreamerLib.device.DevicePicker;
import com.TVStreamerLib.discovery.DiscoveryManager;
import com.TVStreamerLib.discovery.DiscoveryManager.PairingLevel;
import com.TVStreamerLib.sampler.fragments.BaseFragment;
import com.TVStreamerLib.service.DeviceService;
import com.TVStreamerLib.service.DeviceService.PairingType;
import com.TVStreamerLib.service.capability.MediaPlayer;
import com.TVStreamerLib.service.command.ServiceCommandError;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    ConnectableDevice mTV;
    AlertDialog dialog;
    AlertDialog pairingAlertDialog;
    AlertDialog pairingCodeDialog;
    DevicePicker dp;

    MenuItem connectItem;

    SectionsPagerAdapter mSectionsPagerAdapter;

    ViewPager mViewPager;
    ActionBar actionBar;

    private ConnectableDeviceListener deviceListener = new ConnectableDeviceListener() {

        @Override
        public void onPairingRequired(ConnectableDevice device, DeviceService service, PairingType pairingType) {
            Log.d("2ndScreenAPP", "Connected to " + mTV.getIpAddress());

            switch (pairingType) {
                case FIRST_SCREEN:
                    Log.d("2ndScreenAPP", "First Screen");
                    pairingAlertDialog.show();
                    break;

                case PIN_CODE:
                case MIXED:
                    Log.d("2ndScreenAPP", "Pin Code");
                    pairingCodeDialog.show();
                    break;

                case NONE:
                default:
                    break;
            }
        }

        @Override
        public void onConnectionFailed(ConnectableDevice device, ServiceCommandError error) {
            Log.d("2ndScreenAPP", "onConnectFailed");
            connectFailed(mTV);
        }

        @Override
        public void onDeviceReady(ConnectableDevice device) {
            Log.d("2ndScreenAPP", "onPairingSuccess");
            if (pairingAlertDialog.isShowing()) {
                pairingAlertDialog.dismiss();
            }
            if (pairingCodeDialog.isShowing()) {
                pairingCodeDialog.dismiss();
            }
            registerSuccess(mTV);
        }

        @Override
        public void onDeviceDisconnected(ConnectableDevice device) {
            Log.d("2ndScreenAPP", "Device Disconnected");
            connectEnded(mTV);
            connectItem.setTitle("Connect");

            BaseFragment frag = mSectionsPagerAdapter.getFragment(mViewPager.getCurrentItem());
            if (frag != null) {
                Toast.makeText(getApplicationContext(), "Device Disconnected", Toast.LENGTH_SHORT).show();
                frag.disableButtons();
            }
        }

        @Override
        public void onCapabilityUpdated(ConnectableDevice device, List<String> added, List<String> removed) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mSectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        Handler handler = new Handler();
        handler.post(new Runnable() {

            @Override
            public void run() {
                mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        actionBar.setSelectedNavigationItem(position);
                    }
                });

                for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
                    actionBar.addTab(actionBar.newTab()
                            .setIcon(mSectionsPagerAdapter.getIcon(i))
                            .setTabListener(MainActivity.this));
                }
                setupPicker();
            }
        });


        DiscoveryManager.getInstance().setChromecastAppId(getResources().getString(R.string.chromecast_app_id));
        DiscoveryManager.getInstance().registerDefaultDeviceTypes();
        DiscoveryManager.getInstance().setPairingLevel(PairingLevel.ON);
        DiscoveryManager.getInstance().start();


    }

    public List<ConnectableDevice> getImageDevices() {
        List<ConnectableDevice> imageDevices = new ArrayList<ConnectableDevice>();

        for (ConnectableDevice device : DiscoveryManager.getInstance().getCompatibleDevices().values()) {
            if (device.hasCapability(MediaPlayer.Display_Image))
                imageDevices.add(device);
        }

        return imageDevices;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (dialog != null) {
            dialog.dismiss();
        }

        if (mTV != null) {
            mTV.disconnect();
        }
        finish();
        System.exit(0);
    }

    public void hConnectToggle() {
        if (!this.isFinishing()) {
            if (mTV != null) {
                if (mTV.isConnected())
                    mTV.disconnect();

                connectItem.setTitle("Connect");
                mTV.removeListener(deviceListener);
                mTV = null;
                //Update UI after connect/dissconnect
                for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
                    if (mSectionsPagerAdapter.getFragment(i) != null) {
                        mSectionsPagerAdapter.getFragment(i).setTv(null);
                    }
                }
            } else {
                dialog.show();
            }
        }
    }

    private void setupPicker() {
        dp = new DevicePicker(this);
        dialog = dp.getPickerDialog("Device List", new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                mTV = (ConnectableDevice) arg0.getItemAtPosition(arg2);
                mTV.addListener(deviceListener);
                mTV.setPairingType(null);
                mTV.connect();
                connectItem.setTitle(mTV.getFriendlyName());

                dp.pickDevice(mTV);
            }
        });

        pairingAlertDialog = new AlertDialog.Builder(this)
                .setTitle("Pairing with TV")
                .setMessage("Please confirm the connection on your TV")
                .setPositiveButton("Okay", null)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dp.cancelPicker();

                        hConnectToggle();
                    }
                })
                .create();

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        pairingCodeDialog = new AlertDialog.Builder(this)
                .setTitle("Enter Pairing Code on TV")
                .setView(input)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (mTV != null) {
                            String value = input.getText().toString().trim();
                            mTV.sendPairingKey(value);
                            imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dp.cancelPicker();

                        hConnectToggle();
                        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                    }
                })
                .create();
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    void registerSuccess(ConnectableDevice device) {
        Log.d("2ndScreenAPP", "successful register");

        BaseFragment frag = mSectionsPagerAdapter.getFragment(mViewPager.getCurrentItem());
        if (frag != null)
            frag.setTv(mTV);
    }

    void connectFailed(ConnectableDevice device) {
        if (device != null)
            Log.d("2ndScreenAPP", "Failed to connect to " + device.getIpAddress());

        if (mTV != null) {
            mTV.removeListener(deviceListener);
            mTV.disconnect();
            mTV = null;
        }
    }

    void connectEnded(ConnectableDevice device) {
        if (pairingAlertDialog.isShowing()) {
            pairingAlertDialog.dismiss();
        }
        if (pairingCodeDialog.isShowing()) {
            pairingCodeDialog.dismiss();
        }
        mTV.removeListener(deviceListener);
        mTV = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        connectItem = menu.getItem(0);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_connect:
                hConnectToggle();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
        getSupportActionBar().setTitle(mSectionsPagerAdapter.getTitle(tab.getPosition()));
        BaseFragment frag = mSectionsPagerAdapter.getFragment(tab.getPosition());
        if (frag != null)
            frag.setTv(mTV);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }


}

