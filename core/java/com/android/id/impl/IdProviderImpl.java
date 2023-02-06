package com.android.id.impl;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.android.id.IdProvider;

public class IdProviderImpl implements IdProvider {
    private static final String AUTHORITY = "content://com.android.brawn.content/idprovider";
    private static final String COLUMN_NAME = "uniform_id";
    private static final String PATH_AAID = "/aaid";
    private static final String PATH_OAID = "/oaid";
    private static final String PATH_UDID = "/udid";
    private static final String PATH_VAID = "/vaid";
    private static final String TAG = "IdProviderImpl";

    @Override
    public String getUDID(Context context) {
        if (context == null)
            return null;

        Cursor cursor = context.getContentResolver().query(Uri.parse(AUTHORITY + PATH_UDID), null, null, null, null);
        if (cursor != null && cursor.moveToNext()) {
            try {
                return cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            } catch (Exception e) {
                Log.e(TAG, "get UDID exception", e);
            } finally {
                cursor.close();
            }
        }
        return null;
    }

    @Override
    public String getOAID(Context context) {
        if (context == null)
            return null;

        Cursor cursor = context.getContentResolver().query(Uri.parse(AUTHORITY + PATH_OAID), null, null, null, null);
        if (cursor != null && cursor.moveToNext()) {
            try {
                return cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            } catch (Exception e) {
                Log.e(TAG, "get OAID exception", e);
            } finally {
                cursor.close();
            }
        }
        return null;
    }

    @Override
    public String getVAID(Context context) {
        if (context == null)
            return null;

        Cursor cursor = context.getContentResolver().query(Uri.parse(AUTHORITY + PATH_VAID), null, null, null, null);
        if (cursor != null && cursor.moveToNext()) {
            try {
                return cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            } catch (Exception e) {
                Log.e(TAG, "get VAID exception", e);
            } finally {
                cursor.close();
            }
        }
        return null;
    }

    @Override
    public String getAAID(Context context) {
        if (context == null)
            return null;

        Cursor cursor = context.getContentResolver().query(Uri.parse(AUTHORITY + PATH_AAID), null, null, null, null);
        if (cursor != null && cursor.moveToNext()) {
            try {
                return cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            } catch (Exception e) {
                Log.e(TAG, "get AAID exception", e);
            } finally {
                cursor.close();
            }
        }
        return null;
    }
}
