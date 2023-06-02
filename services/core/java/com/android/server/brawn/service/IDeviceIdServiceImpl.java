package com.android.server.brawn.service;

import android.os.RemoteException;

import com.android.server.BrawnManager;

public class IDeviceIdServiceImpl extends IDeviceIdService.Stub {
    @Override
    public String getOAID() throws RemoteException {
        return BrawnManager.getInstance().getOaid();
    }

    @Override
    public String getVAID(String str) throws RemoteException {
        return BrawnManager.getInstance().getVaid();
    }

    @Override
    public String getAAID(String str) throws RemoteException {
        return BrawnManager.getInstance().getAaid();
    }
}
