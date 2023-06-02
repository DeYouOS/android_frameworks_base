package com.android.server.brawn.service;

import android.os.RemoteException;

import com.android.server.BrawnManager;

public class IAdvertisingIdServiceImpl extends IAdvertisingIdService.Stub {
    @Override
    public String getId() throws RemoteException {
        return BrawnManager.getInstance().getOaid();
    }

    @Override
    public boolean isLimitAdTrackingEnabled(boolean boo) throws RemoteException {
        return false;
    }
}