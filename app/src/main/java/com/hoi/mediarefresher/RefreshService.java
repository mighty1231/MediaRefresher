package com.hoi.mediarefresher;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;

import java.io.File;

public class RefreshService extends TileService {
    public RefreshService() {
    }

    @Override
    public void onTileAdded() {
        super.onTileAdded();

        Tile qsTile = getQsTile();
        qsTile.setState(Tile.STATE_ACTIVE);
        qsTile.updateTile();
    }

    @Override
    public void onClick() {
        super.onClick();

        Tile qsTile = getQsTile();
        qsTile.setLabel("Refreshing...");
        qsTile.updateTile();
        refresh(qsTile);
    }

    public void refresh(Tile qsTile) {
        String path = Environment.getExternalStorageDirectory().toString() + "/Gorealra";
        File directory = new File(path);
        File[] files = directory.listFiles();

        for (int i=0; i<files.length; i++) {
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" +
                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/Gorealra/" + files[i].getName())));
        }
        qsTile.setLabel("Found " + files.length);
        qsTile.updateTile();
    }
}
