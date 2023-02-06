/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.server;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.brawn.IBrawn;
import android.hardware.brawn.IDeviceInfo;
import android.hardware.brawn.IDeviceServer;
import android.hardware.brawn.IParam;
import android.hardware.brawn.IUserServer;
import android.hardware.brawn.ICallback;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Performs a number of miscellaneous, non-system-critical actions
 * after the system has finished booting.
 */
public class BrawnManager {

    public static String TAG = BrawnManager.class.getSimpleName();

    private static final BrawnManager INSTANCE = new BrawnManager();

    protected BrawnManager() {}

    public static BrawnManager getInstance() {
        return INSTANCE;
    }

    private final AtomicReference<IBrawn> mIBrawn = new AtomicReference<>();
    public IBrawn getBrawnService() {
        if(null == mIBrawn.get() || !mIBrawn.get().asBinder().isBinderAlive() || !mIBrawn.get().asBinder().pingBinder()){
            IBinder b = ServiceManager.getService(IBrawn.DESCRIPTOR + "/default");
            try {
                b.linkToDeath(() -> mIBrawn.set(null), 0);
            } catch (RemoteException ignored) {}
            mIBrawn.set(IBrawn.Stub.asInterface(b));
        }
        return mIBrawn.get();
    }

    private final AtomicReference<IDeviceServer> mIDeviceServer = new AtomicReference<>();
    public IDeviceServer getDeviceServer() throws RemoteException {
        if(null == mIDeviceServer.get() || !mIDeviceServer.get().asBinder().isBinderAlive() || !mIDeviceServer.get().asBinder().pingBinder()){
            mIDeviceServer.set(getBrawnService().getDeviceServer());
            try {
                mIDeviceServer.get().asBinder().linkToDeath(() -> mIDeviceServer.set(null), 0);
            } catch (RemoteException ignored) {}
        }
        return mIDeviceServer.get();
    }

    public void executeRescueLevelInternal(int level) {
        try {
            getDeviceServer().executeRescueLevelInternal(level);
        } catch (RemoteException e) {
            Log.e(TAG, "executeRescueLevelInternal", e);
        }
    }
}
