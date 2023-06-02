/*
 * Copyright (C) 2018 The Android Open Source Project
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

package com.android.server.brawn;

import android.annotation.SuppressLint;
import android.app.ContentProviderHolder;
import android.app.IServiceConnection;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.android.server.BrawnManager;
import com.android.server.brawn.proxy.ProxyMeizuProvider;
import com.android.server.brawn.proxy.ProxyVivoProvider;
import com.android.server.brawn.proxy.ProxyXiaomiProvider;
import com.android.server.brawn.service.IAdvertisingIdServiceImpl;
import com.android.server.brawn.service.IDeviceIdManagerImpl;
import com.android.server.brawn.service.IDeviceIdServiceImpl;
import com.android.server.brawn.service.IDeviceidInterfaceImpl;
import com.android.server.brawn.service.IDidAidlInterfaceImpl;
import com.android.server.brawn.service.IOpenIDImpl;
import com.android.server.brawn.service.IdsSupplierImpl;
import com.android.server.brawn.service.MsaIdInterfaceImpl;
import com.android.server.brawn.service.OpenDeviceIdentifierServiceImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public final class BrawnVirtualIdInternal {

    public static String TAG = BrawnVirtualIdInternal.class.getSimpleName();

    @SuppressLint("StaticFieldLeak")
    private static final BrawnVirtualIdInternal INSTANCE = new BrawnVirtualIdInternal();

    private final String[] mPackageName = new String[]{"com.asus.msa.SupplementaryDID", "com.coolpad.deviceidsupport", "com.android.creator",
            "com.android.vending", "com.huawei.hwid", "com.huawei.hwid.tv", "com.huawei.hms", "com.zui.deviceidservice",
            "com.meizu.flyme.openidsdk", "com.mdid.msa", "com.heytap.openid", "com.samsung.android.deviceidservice"};

    private Context mContext;

    private final Map<String, ContentProviderHolder> mContentProviderMap = new HashMap<>();
    private final Map<String, Binder> mServiceMap = new HashMap<>();
    private BrawnVirtualIdInternal() {}

    public static BrawnVirtualIdInternal getInstance() {
        return INSTANCE;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public boolean isPackageInfo(String packageName) {
        if(!BrawnManager.getInstance().IsLogin())
            return false;

        return Arrays.asList(mPackageName).contains(packageName);
    }

    public boolean bindServiceEx(Intent service, IServiceConnection connection) {
        if(null == service.getPackage() || null == service.getAction())
            return false;

        if(isPackageInfo(service.getPackage()) || "com.google.android.gms".equals(service.getPackage())) {
            if ("com.uodis.opendevice.OPENIDS_SERVICE".equals(service.getAction())) {
                String packageName = "com.uodis.opendevice";
                Binder res = mServiceMap.get(packageName);
                if(null != res) {
                    ServiceConnected(ComponentName.createRelative(packageName, ".Service"), connection, res);
                    return true;
                }
                res = new OpenDeviceIdentifierServiceImpl();
                mServiceMap.put(packageName, res);
                ServiceConnected(ComponentName.createRelative(packageName, ".Service"), connection, res);
                return true;
            } else if ("com.bun.msa.action.start.service".equals(service.getAction())) {
                String packageName = "com.bun.msa";
                Binder res = mServiceMap.get(packageName);
                if(null != res) {
                    ServiceConnected(ComponentName.createRelative(packageName, ".Service"), connection, res);
                    return true;
                }
                res = new MsaIdInterfaceImpl();
                mServiceMap.put(packageName, res);
                ServiceConnected(ComponentName.createRelative(packageName, ".Service"), connection, res);
                return true;
            } else if ("com.google.android.gms.ads.identifier.service.START".equals(service.getAction())) {
                String packageName = "com.google.android.gms";
                Binder res = mServiceMap.get(packageName);
                if(null != res) {
                    ServiceConnected(ComponentName.createRelative(packageName, ".Service"), connection, res);
                    return true;
                }
                res = new IAdvertisingIdServiceImpl();
                mServiceMap.put(packageName, res);
                ServiceConnected(ComponentName.createRelative(packageName, ".Service"), connection, res);
                return true;
            } else if ("android.service.action.msa".equals(service.getAction())) {
                String packageName = "android.service.action.msa";
                Binder res = mServiceMap.get(packageName);
                if(null != res) {
                    ServiceConnected(ComponentName.createRelative(packageName, ".Service"), connection, res);
                    return true;
                }
                res = new IdsSupplierImpl();
                mServiceMap.put(packageName, res);
                ServiceConnected(ComponentName.createRelative(packageName, ".Service"), connection, res);
                return true;
            } else if ("com.bun.msa.action.bindto.service".equals(service.getAction())) {
                String packageName = "com.mdid.msa";
                Binder res = mServiceMap.get(packageName);
                if(null != res) {
                    ServiceConnected(ComponentName.createRelative(packageName, ".Service"), connection, res);
                    return true;
                }
                res = new MsaIdInterfaceImpl();
                mServiceMap.put(packageName, res);
                ServiceConnected(ComponentName.createRelative(packageName, ".Service"), connection, res);
                return true;
            }
            Log.d(TAG, "NULL.Action" + service.getAction());
        }
        return false;
    }

    public boolean bindService(Intent service, IServiceConnection connection) {
        
        if(!BrawnManager.getInstance().IsLogin())
            return false;

        if(bindServiceEx(service, connection))
            return true;

        if(null != service.getComponent() && "com.samsung.android.deviceidservice/.DeviceIdService".equals(service.getComponent().flattenToShortString())) {
            String packageName = "com.samsung.android.deviceidservice";
            Binder res = mServiceMap.get(packageName);
            if(null != res) {
                ServiceConnected(ComponentName.createRelative(packageName, ".Service"), connection, res);
                return true;
            }
            res = new IDeviceIdServiceImpl();
            mServiceMap.put(packageName, res);
            ServiceConnected(ComponentName.createRelative(packageName, ".Service"), connection, res);
            return true;
        } else if(null != service.getComponent() && "com.heytap.openid/.IdentifyService".equals(service.getComponent().flattenToShortString())) {
            String packageName = "com.heytap.openid";
            Binder res = mServiceMap.get(packageName);
            if(null != res) {
                ServiceConnected(ComponentName.createRelative(packageName, ".Service"), connection, res);
                return true;
            }
            res = new IOpenIDImpl();
            mServiceMap.put(packageName, res);
            ServiceConnected(ComponentName.createRelative(packageName, ".Service"), connection, res);
            return true;
        } else if(null != service.getComponent() && "com.coolpad.deviceidsupport/.DeviceIdService".equals(service.getComponent().flattenToShortString())) {
            String packageName = "com.coolpad.deviceidsupport";
            Binder res = mServiceMap.get(packageName);
            if(null != res) {
                ServiceConnected(ComponentName.createRelative(packageName, ".Service"), connection, res);
                return true;
            }
            res = new IDeviceIdManagerImpl();
            mServiceMap.put(packageName, res);
            ServiceConnected(ComponentName.createRelative(packageName, ".Service"), connection, res);
            return true;
        } else if(null != service.getComponent() && "com.asus.msa.SupplementaryDID/.SupplementaryDIDService".equals(service.getComponent().flattenToShortString())) {
            String packageName = "com.asus.msa.SupplementaryDID";
            Binder res = mServiceMap.get(packageName);
            if(null != res) {
                ServiceConnected(ComponentName.createRelative(packageName, ".Service"), connection, res);
                return true;
            }
            res = new IDidAidlInterfaceImpl();
            mServiceMap.put(packageName, res);
            ServiceConnected(ComponentName.createRelative(packageName, ".Service"), connection, res);
            return true;
        } else if(null != service.getComponent() && "com.zui.deviceidservice/.DeviceidService".equals(service.getComponent().flattenToShortString())) {
            String packageName = "com.zui.deviceidservice";
            Binder res = mServiceMap.get(packageName);
            if(null != res) {
                ServiceConnected(ComponentName.createRelative(packageName, ".Service"), connection, res);
                return true;
            }
            res = new IDeviceidInterfaceImpl();
            mServiceMap.put(packageName, res);
            ServiceConnected(ComponentName.createRelative(packageName, ".Service"), connection, res);
            return true;
        } else if(null != service.getComponent() && "com.mdid.msa/.service.MsaIdService".equals(service.getComponent().flattenToShortString())) {
            String packageName = "com.mdid.msa";
            Binder res = mServiceMap.get(packageName);
            if(null != res) {
                ServiceConnected(ComponentName.createRelative(packageName, ".Service"), connection, res);
                return true;
            }
            res = new MsaIdInterfaceImpl();
            mServiceMap.put(packageName, res);
            ServiceConnected(ComponentName.createRelative(packageName, ".Service"), connection, res);
            return true;
        }
        return false;
    }

    private void ServiceConnected(ComponentName name, IServiceConnection connection, IBinder service) {
        try {
            connection.connected(name, service, false);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public ContentProviderHolder getContentProvider(String authority) {
        if(!BrawnManager.getInstance().IsLogin() || !checkContentProviderAccess(authority))
            return null;

        ContentProviderHolder res = mContentProviderMap.get(authority);
        if(null != res)
            return res;

        if ("com.vivo.vms.IdProvider".equals(authority)) {
            res = newProxyContentProvider(authority, new ProxyVivoProvider());
            mContentProviderMap.put(authority, res);
            return res;
        } else if ("com.miui.idprovider".equals(authority)){
            res = newProxyContentProvider(authority, new ProxyXiaomiProvider());
            mContentProviderMap.put(authority, res);
            return res;
        } else if ("com.meizu.flyme.openidsdk".equals(authority)){
            res = newProxyContentProvider(authority, new ProxyMeizuProvider());
            mContentProviderMap.put(authority, res);
            return res;
        }
        return null;
    }

    public ContentProviderHolder newProxyContentProvider(String authority, ContentProvider contentProvider) {
        ProviderInfo info = new ProviderInfo();
        info.authority = authority;
        info.exported = true;
        info.applicationInfo = mContext.getApplicationInfo();
        ContentProviderHolder holder = new ContentProviderHolder(info);
        contentProvider.attachInfo(mContext, info);
        holder.provider = contentProvider.getIContentProvider();
        return holder;
    }

    public boolean checkContentProviderAccess(String authority) {
        return "com.vivo.vms.IdProvider".equals(authority) || "com.miui.idprovider".equals(authority) || "com.meizu.flyme.openidsdk".equals(authority);
    }

    public ProviderInfo resolveContentProvider(String authority) {
        if(!BrawnManager.getInstance().IsLogin() || !checkContentProviderAccess(authority))
            return null;

        ProviderInfo info = new ProviderInfo();
        info.authority = authority;
        info.exported = true;
        info.applicationInfo = mContext.getApplicationInfo();
        return info;
    }
}
