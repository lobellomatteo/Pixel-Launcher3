// 
// Decompiled by Procyon v0.5.30
// 

package com.google.android.apps.nexuslauncher;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;

import com.android.launcher3.IconProvider;
import com.android.launcher3.LauncherModel;
import com.google.android.apps.nexuslauncher.b2.c;

import java.util.Calendar;

public class DynamicIconProvider extends IconProvider
{
    private final BroadcastReceiver fJ;
    private final Context mContext;
    private final PackageManager mPackageManager;
    
    public DynamicIconProvider(final Context mContext) {
        this.fJ = new e(this);
        final IntentFilter intentFilter = new IntentFilter("android.intent.action.DATE_CHANGED");
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        mContext.registerReceiver(this.fJ, intentFilter, (String)null, new Handler(LauncherModel.getWorkerLooper()));
        this.mContext = mContext;
        this.mPackageManager = mContext.getPackageManager();
    }
    
    @SuppressLint("WrongConstant")
    private int em() {
        return Calendar.getInstance().get(5) - 1;
    }
    
    private int en(final Bundle bundle, final Resources resources) {
        if (bundle == null) {
            return 0;
        }
        final int int1 = bundle.getInt("com.google.android.calendar.dynamic_icons_nexus_round", 0);
        if (int1 == 0) {
            return 0;
        }
        final TypedArray obtainTypedArray = resources.obtainTypedArray(int1);
        try {
            return obtainTypedArray.getResourceId(this.em(), 0);
        }
        catch (Resources.NotFoundException ex) {
            return 0;
        }
    }
    
    private boolean eo(final String s) {
        return "com.google.android.calendar".equals(s);
    }
    
    public Drawable getIcon(final LauncherActivityInfo launcherActivityInfo, final int n, final boolean b) {
        Drawable drawable = null;
        final String packageName = launcherActivityInfo.getApplicationInfo().packageName;
        Label_0117: {
            if (!this.eo(packageName)) {
                break Label_0117;
            }
            final PackageManager mPackageManager = this.mPackageManager;
            try {
                @SuppressLint("WrongConstant") final ActivityInfo activityInfo = mPackageManager.getActivityInfo(launcherActivityInfo.getComponentName(), 8320);
                final Bundle metaData = activityInfo.metaData;
                final Resources resourcesForApplication = this.mPackageManager.getResourcesForApplication(packageName);
                final int en = this.en(metaData, resourcesForApplication);
                if (en != 0) {
                    drawable = resourcesForApplication.getDrawableForDensity(en, n);
                }
                if (drawable == null) {
                    drawable = super.getIcon(launcherActivityInfo, n);
                }
                if (b || !c.fk.equals((Object)launcherActivityInfo.getComponentName()) /*|| !Process.myUserHandle().equals((Object)launcherActivityInfo.getUser())*/ || !this.mContext.getResources().getString(R.string.drawable_factory_class).equals((Object)DynamicDrawableFactory.class.getName()))
                    drawable = c.dK(this.mContext, n);
            }
            catch (NameNotFoundException ex2) {}
        }
        return drawable;
    }
    
    public String getIconSystemState(final String s) {
        if (this.eo(s)) {
            return this.mSystemState + " " + this.em();
        }
        return this.mSystemState;
    }
}