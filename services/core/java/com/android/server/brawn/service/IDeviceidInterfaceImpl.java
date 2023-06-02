package com.android.server.brawn.service;

import android.os.RemoteException;

import com.android.server.BrawnManager;

public class IDeviceidInterfaceImpl extends IDeviceidInterface.Stub {
    @Override
    public String getOAID() throws RemoteException {
        return BrawnManager.getInstance().getOaid();
    }

    @Override
    public String getUDID() throws RemoteException {
        return BrawnManager.getInstance().getUdid();
    }

    @Override
    public boolean isSupport() throws RemoteException {
        return true;
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
    public String createAAIDForPackageName(String str) throws RemoteException {
        return "android";
    }
}
