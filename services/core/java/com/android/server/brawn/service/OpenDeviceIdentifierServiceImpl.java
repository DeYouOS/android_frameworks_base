package com.android.server.brawn.service;

import android.os.RemoteException;

import com.android.server.BrawnManager;

public class OpenDeviceIdentifierServiceImpl extends OpenDeviceIdentifierService.Stub {
    @Override
    public String getOaid() throws RemoteException {
        return BrawnManager.getInstance().getOaid();
    }

    @Override
    public boolean isOaidTrackLimited() throws RemoteException {
        return false;
    }
}
