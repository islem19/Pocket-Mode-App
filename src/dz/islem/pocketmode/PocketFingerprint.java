package dz.islem.pocketmode;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

/**
 * <p>Title: PocketFingerprint</p>
 * <p>Description: Disable and Enable the Fingerprint sensor using the device policy manager</p>
 * <p>All functions related to PocketFingerprint are implemented here</p>
 * @author Abdelkader SELLAMI
 * @version 1.0
 */
public class PocketFingerprint {

    private static final boolean DEBUG = false;
    private static final String TAG = "AospPocketMode";
    private DevicePolicyManager mDevicePolicyManager;
    private ComponentName mComponentName;
    private Context mContext;

    public PocketFingerprint(Context context) {
        mContext = context;
        mDevicePolicyManager =(DevicePolicyManager) mContext.getSystemService(mContext.DEVICE_POLICY_SERVICE);
        mComponentName=new ComponentName(mContext, PocketAdminReceiver.class);
    }

    /**
     * Disable the fingerprint sensor
     */
    public void DisableFingerprint() {
        if (DEBUG) Log.d(TAG, "Disabling Fingerprint");
        if (isAdminActive())
            // call the keyguard using the device policy manager
            mDevicePolicyManager.setKeyguardDisabledFeatures(mComponentName, DevicePolicyManager.KEYGUARD_DISABLE_FINGERPRINT);

    }

    /**
     * Enable the fingerprint sensor
     */
    public void EnableFingerprint() {
        if (DEBUG) Log.d(TAG, "Enabling Fingerprint");
        if (isAdminActive())
                // call the keyguard using the device policy manager
                mDevicePolicyManager.setKeyguardDisabledFeatures(mComponentName, DevicePolicyManager.KEYGUARD_DISABLE_FEATURES_NONE);
    }

    /**
     * check if the App is Admin Active
     */
    public boolean isAdminActive() {
        if (DEBUG) Log.d(TAG, "is Admin Active");
        return mDevicePolicyManager.isAdminActive(mComponentName);
    }

}