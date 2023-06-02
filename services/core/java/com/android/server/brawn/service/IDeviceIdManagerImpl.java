package com.android.server.brawn.service;

import android.os.RemoteException;

import com.android.server.BrawnManager;

public class IDeviceIdManagerImpl extends IDeviceIdManager.Stub {
    @Override
    public String getUDID(String str) throws RemoteException {
        return BrawnManager.getInstance().getUdid();
    }

    @Override
    public String getOAID(String str) throws RemoteException {
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

    @Override
    public String getIMEI(String str) throws RemoteException {
        return "";
    }

    @Override
    public boolean isCoolOs() throws RemoteException {
        return true;
    }

    @Override
    public String getCoolOsVersion() throws RemoteException {
        return "1.0.0";
    }
}
