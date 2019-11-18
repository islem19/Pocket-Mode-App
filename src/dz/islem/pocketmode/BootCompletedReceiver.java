package dz.islem.pocketmode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootCompletedReceiver extends BroadcastReceiver {
    private static final boolean DEBUG = true;
    private static final String TAG = "AospPocketMode";

    @Override
    public void onReceive(Context context, Intent intent) {
            if (DEBUG) Log.d(TAG, "BootCompletedReceiver Starting");
            // Start the Pocket Mode Service
            context.startService(new Intent(context, PocketModeService.class));

    }
}
