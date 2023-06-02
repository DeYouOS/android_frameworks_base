package com.android.server.brawn.service;

import android.os.RemoteException;

import com.android.server.BrawnManager;

public class IOpenIDImpl extends IOpenID.Stub {
    @Override
    public String getSerID(String pkgName, String sign, String type) throws RemoteException {
        return BrawnManager.getInstance().getOaid();
    }
}
