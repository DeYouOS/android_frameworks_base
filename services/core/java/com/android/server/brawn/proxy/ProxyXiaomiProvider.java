package com.android.server.brawn.proxy;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.server.BrawnManager;

public class ProxyXiaomiProvider extends BaseProxyProvider {

    private Cursor retUniform_id(String uniform_id) {
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"uniform_id"}, 1);
        matrixCursor.addRow(new String[]{uniform_id});
        return matrixCursor;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if(uri.getPath() == null)
            return null;

        if(uri.getPath().endsWith("aaid"))
            return retUniform_id(BrawnManager.getInstance().getAaid());

        if(uri.getPath().endsWith("oaid"))
            return retUniform_id(BrawnManager.getInstance().getOaid());

        if(uri.getPath().endsWith("udid"))
            return retUniform_id(BrawnManager.getInstance().getUdid());

        if(uri.getPath().endsWith("vaid"))
            return retUniform_id(BrawnManager.getInstance().getVaid());

        return retUniform_id(BrawnManager.getInstance().getOaid());
    }

}
