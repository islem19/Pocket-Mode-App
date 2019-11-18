package dz.islem.pocketmode;

import android.content.Context;
import android.content.Intent;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.UserHandle;
import android.support.v7.preference.PreferenceManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;


import static android.provider.Settings.Secure.DOZE_ENABLED;

public final class Utils {

    public static final String TAG = "PocketUtils";
    public static final boolean DEBUG = false;
    public static final String DOZE_INTENT = "com.android.systemui.doze.pulse";

    /**
     * check if the doze mode is enabled or not
     * @param context
     * @return boolean, return true if doze mode is enabled
     */
    public static boolean isDozeEnabled(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(),
                DOZE_ENABLED, 1) != 0;
    }

    public static boolean checkDozeService(Context context) {
        if (isDozeEnabled(context)) return true;
        return false;
    }

    /**
     * Start the doze service and send a broadcast
     * @param context
     */
    public static void launchDozePulse(Context context) {
        if (DEBUG) Log.d(TAG, "Launch doze pulse");
        context.sendBroadcastAsUser(new Intent(DOZE_INTENT),
                new UserHandle(UserHandle.USER_CURRENT));
    }

    /**
     * Enable the Doze Service
     * @param context
     * @param enable: boolean
     * @return boolean, return true if doze mode is enabled
     */
    public static boolean enableDoze(Context context, boolean enable) {
        return Settings.Secure.putInt(context.getContentResolver(),
                DOZE_ENABLED, enable ? 1 : 0);
    }

    /**
     * check if the fingerprint sensor is actived and exsits
     * @param context
     * @return boolean, return true if fingerprint is active
     */
    public static boolean isFingerprintSensor(Context context) {
        FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(context.FINGERPRINT_SERVICE);
        if (!fingerprintManager.isHardwareDetected() || !fingerprintManager.hasEnrolledFingerprints() ) return false;
        return true;
    }

    /**
     * check if the App is Device Admin active
     * @param context
     * @return boolean, return true if App is device admin active
     */
    public static boolean isAdminActive(Context context){
        DevicePolicyManager mDevicePolicyManager =(DevicePolicyManager) context.getSystemService(context.DEVICE_POLICY_SERVICE);
        ComponentName mComponentName=new ComponentName(context, PocketAdminReceiver.class);
        return mDevicePolicyManager.isAdminActive(mComponentName);
    }




}