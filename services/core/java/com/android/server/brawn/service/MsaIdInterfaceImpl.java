package com.android.server.brawn.service;

import android.os.RemoteException;

import com.android.server.BrawnManager;

public class MsaIdInterfaceImpl extends MsaIdInterface.Stub {
    @Override
    public boolean isSupported() throws RemoteException {
        return true;
    }

    @Override
    public boolean isDataArrived() throws RemoteException {
        return true;
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

    @Override
    public void shutDown() throws RemoteException {

    }
}
