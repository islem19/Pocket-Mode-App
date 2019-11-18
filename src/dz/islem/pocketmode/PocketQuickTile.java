package dz.islem.pocketmode;

import android.content.ComponentName;
import android.content.Intent;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.widget.Toast;

/**
 * <p>Title: PocketQuickTile</p>
 * <p>Description: Adds a Quick Tile on Status Bar by Extending the Tile Service  </p>
 * <p>All functions related to PocketQuickTile are implemented here</p>
 * @author Abdelkader SELLAMI
 * @version 1.0
 */
public class PocketQuickTile extends TileService {
    private Tile mTile;
    private final int STATE_UNAVAILABLE = 0;
    private final int STATE_INACTIVE = 1;
    private final int STATE_ACTIVE = 2;

    @Override
    public void onTileAdded() {
        super.onTileAdded();
        UpdateState();
    }

    @Override
    public void onTileRemoved() {
        super.onTileRemoved();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        UpdateState();
    }

    @Override
    public void onStopListening() {
        super.onStopListening();
    }

    @Override
    public void onClick() {
        super.onClick();
        // switch the state of the time programatically
        int SwitchState = mTile.getState() == STATE_ACTIVE ? STATE_INACTIVE : STATE_ACTIVE;
        mTile.setState(SwitchState);
        // update the tile
        mTile.updateTile();

        String msg = CheckState() == STATE_ACTIVE ? "Disable" : "Enable";
        Toast.makeText(getApplicationContext(), " Please "+ msg +" islem Pocket Mode" , Toast.LENGTH_LONG).show();

        //Start the Device Admin Settings Activity to enable and disable the App Admin
        startActivityAndCollapse(new Intent().setComponent(new ComponentName("com.android.settings", "com.android.settings.DeviceAdminSettings")).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    /**
     * Check the state if the App is device Admin Active
     * @return integer, either State Active or Inactive
     */
    private int CheckState() {
        return  Utils.isAdminActive(getApplicationContext()) ? STATE_ACTIVE : STATE_INACTIVE;
    }

    /**
     * Update the Tile State
     */
    private void UpdateState() {
        mTile = getQsTile();
        mTile.setState(CheckState());
        mTile.updateTile();
    }
}