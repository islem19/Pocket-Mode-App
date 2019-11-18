package dz.islem.pocketmode;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * <p>Title: PocketSensor</p>
 * <p>Description: Manage the difference sensors: Proximity and Accelerometer </p>
 * <p>All functions related to PocketSensor are implemented here</p>
 * @author Abdelkader SELLAMI
 * @version 1.0
 */
public class PocketSensor {

    private static final boolean DEBUG = true;
    private static final String TAG = "AospPocketMode";

    private SensorManager mSensorManager;
    private Sensor mProximitySensor;
    private Sensor mAccelerometerSensor;
    private Context mContext;
    private float mAccelCurrent = SensorManager.GRAVITY_EARTH;
    private float mAccelLast = SensorManager.GRAVITY_EARTH;
    private float mAccel = 0.00f;
    private PocketFingerprint mPocketFingerprint;

    public PocketSensor(Context context) {
        mContext = context;
        mSensorManager = (SensorManager) mContext.getSystemService(mContext.SENSOR_SERVICE);
        mProximitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mPocketFingerprint = new PocketFingerprint(mContext);
    }

    /**
     * Enable the sensors and register listeners for both Proximity and Accelerometer
     */
    protected void enable() {
        if (DEBUG) Log.d(TAG, "Enabling");
        mSensorManager.registerListener(mProximityListener, mProximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mAccelerometerListener, mAccelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * Disable the sensors and unregister listeners for both Proximity and Accelerometer
     */
    protected void disable() {
        if (DEBUG) Log.d(TAG, "Disabling");
        if (Utils.isFingerprintSensor(mContext)) mPocketFingerprint.EnableFingerprint();
        mSensorManager.unregisterListener(mProximityListener, mProximitySensor);
        mSensorManager.unregisterListener(mAccelerometerListener, mAccelerometerSensor);
    }

    /**
     * The Proximity Sensor Listener
     */
    SensorEventListener mProximityListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            // check if the proximity sensor is covered
            boolean isNear = event.values[0] < mProximitySensor.getMaximumRange();

            if (isNear) {
                if (DEBUG) Log.d(TAG, "Phone in Pocket Detected");
                // check if the fingerprint is active and then disable it
                if (Utils.isFingerprintSensor(mContext)) mPocketFingerprint.DisableFingerprint();
                // unregister the acceletometer sensro listener
                mSensorManager.unregisterListener(mAccelerometerListener, mAccelerometerSensor);
            }
            else {
                if (DEBUG) Log.d(TAG, "Phone in Pocket Not Detected");
                // check if the fingerprint is active and then re-enable it again
                if (Utils.isFingerprintSensor(mContext)) mPocketFingerprint.EnableFingerprint();
                //register the acceletometer sensro listener back
                mSensorManager.registerListener(mAccelerometerListener, mAccelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            /* Empty */
        }
    };

    /**
     * The Proximity Sensor Listener
     */
    SensorEventListener mAccelerometerListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            // get teh vent values
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            mAccelLast = mAccelCurrent;
            // calculate the Acceleration
            mAccelCurrent = (float) Math.sqrt(x*x + y*y + z*z);
            // recalculate the difference between last movement and current one with taking into account the gravity
            mAccel = mAccel * 0.9f + (mAccelCurrent - mAccelLast);
            // the delta difference is 3, which is experimented not calculated
            if(mAccel < 3){
                Log.e(TAG, "onSensorChanged: device stationarry");
                //Log.d(TAG,"Prox Doze is :" + Utils.isDozeEnabled(mContext));
                //if (Utils.checkDozeService(mContext)) Utils.launchDozePulse(mContext);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            /* Empty */
        }
    };

}
