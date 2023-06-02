package com.android.server.brawn.service;

import android.os.RemoteException;

import com.android.server.BrawnManager;

public class IDidAidlInterfaceImpl extends IDidAidlInterface.Stub {
    @Override
    public boolean isSupport() throws RemoteException {
        return true;
    }

    @Override
    public String getUDID() throws RemoteException {
        return BrawnManager.getInstance().getUdid();
    }

    @Override
    public String getOAID() throws RemoteException {
        return BrawnManager.getInstance().getOaid();
    }

    @Override
    public String getVAID() throws RemoteException {
        return BrawnManager.getInstance().getVaid();
    }

    @Override
    public String getAAID() throws RemoteException {
        return BrawnManager.getInstance().getAaid();
    }
}
