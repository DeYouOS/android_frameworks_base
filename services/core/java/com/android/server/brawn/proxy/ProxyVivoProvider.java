package com.android.server.brawn.proxy;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.server.BrawnManager;

public class ProxyVivoProvider extends BaseProxyProvider {

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    private Cursor retUniform_id(Uri uri, String key, String uniform_id) {
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"id", "key","value"}, 3);
        matrixCursor.addRow(new String[]{"0", key, uniform_id});
        mHandler.postDelayed(() -> {
            getContext().getContentResolver().notifyChange(uri, null);
        }, 100);
        return matrixCursor;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if(uri.getPath() == null)
            return null;

        if(uri.getPath().endsWith("OAIDSTATUS"))
            return retUniform_id(uri,"OAIDSTATUS","0");

        if(uri.getPath().contains("OAID"))
            return retUniform_id(uri, "OAID", BrawnManager.getInstance().getOaid());

        if(uri.getPath().contains("VAID"))
            return retUniform_id(uri, "VAID", BrawnManager.getInstance().getVaid());

        if(uri.getPath().contains("AAID"))
            return retUniform_id(uri, "AAID", BrawnManager.getInstance().getAaid());

        if(uri.getPath().contains("UDID"))
            return retUniform_id(uri, "UDID", BrawnManager.getInstance().getUdid());

        return null;
    }
}
