package com.android.server.brawn.service;

import android.os.RemoteException;

import com.android.server.BrawnManager;

public class IdsSupplierImpl extends IdsSupplier.Stub {
    @Override
    public boolean isSupported() throws RemoteException {
        return true;
    }

    @Override
    public String getUDID(String str) throws RemoteException {
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
    public String getAAID(String str) throws RemoteException {
        return BrawnManager.getInstance().getAaid();
    }
}
