// 
// Decompiled by Procyon v0.5.30
// 

package com.google.android.apps.nexuslauncher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.launcher3.Utilities;

public class SetupWizardStartReceiver extends BroadcastReceiver
{
    public void onReceive(final Context context, final Intent intent) {
        Utilities.getDevicePrefs(context).edit().putString("data_import_src_pkg", "com.google.android.googlequicksearchbox").putString("data_import_src_authority", "com.google.android.launcher.settings").putBoolean("pref_show_discovery_bounce", true).commit();
    }
}