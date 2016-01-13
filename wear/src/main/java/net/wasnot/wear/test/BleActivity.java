package net.wasnot.wear.test;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;
import android.widget.Toast;

public class BleActivity extends Activity {

    private TextView mTextView;
    private BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning;
    private Handler mHandler;

    private static final int REQUEST_ENABLE_BT = 1;
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.bleText);
                if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
                    Toast.makeText(BleActivity.this, "BLE is not supported", Toast.LENGTH_SHORT)
                            .show();
                    finish();
                    return;
                }

                Toast.makeText(BleActivity.this, "BLE is supported", Toast.LENGTH_SHORT)
                        .show();
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

                // Checks if Bluetooth is supported on the device.
                if (mBluetoothAdapter == null) {
                    Toast.makeText(BleActivity.this, "bluetooth not supported", Toast.LENGTH_SHORT)
                            .show();
//                    finish();
                    return;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.
        if (mBluetoothAdapter != null && !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        scanLeDevice(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        scanLeDevice(false);
    }

    private void scanLeDevice(final boolean enable) {
        if (mBluetoothAdapter == null) {
            return;
        }
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    invalidateOptionsMenu();
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
        invalidateOptionsMenu();
    }

    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

                @Override
                public void onLeScan(final BluetoothDevice device, final int rssi,
                        final byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTextView.append(device + "\n");
                        }
                    });
                }
            };

}
