package dz.islem.pocketmode;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

/**
 * <p>Title: PocketModeService</p>
 * <p>Description: Pocket Mode Service, register a local broadcast receiver on screen on/off, enable and disable the Pocket Sensors </p>
 * <p>All functions related to PocketModeService are implemented here</p>
 * @author Abdelkader SELLAMI
 * @version 1.0
 */
public class PocketModeService extends Service {
    private static final String TAG = "AospPocketMode";
    private static final boolean DEBUG = true;
    private PocketSensor mPocketSensor;


    @Override
    public void onCreate() {
        if (DEBUG) Log.d(TAG, "Creating service");
        mPocketSensor = new PocketSensor(this);

        // register a local broadcast receiver on screen on/off
        IntentFilter screenStateFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        screenStateFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mScreenStateReceiver, screenStateFilter);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (DEBUG) Log.d(TAG, "Starting service");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (DEBUG) Log.d(TAG, "Destroying service");
        super.onDestroy();
        this.unregisterReceiver(mScreenStateReceiver);
        mPocketSensor.disable();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void onDisplayOn() {
        if (DEBUG) Log.d(TAG, "Display on");
        // disable the sensors when the display is on
        mPocketSensor.disable();
    }

    private void onDisplayOff() {
        if (DEBUG) Log.d(TAG, "Display off");
        // enable the sensors when the display is off
        mPocketSensor.enable();
    }

    private BroadcastReceiver mScreenStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                onDisplayOn();
            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                onDisplayOff();
            }
        }
    };
}
