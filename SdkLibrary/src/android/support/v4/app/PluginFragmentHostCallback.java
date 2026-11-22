/* Copyright 2018 iQIYI.com. Licensed under the Apache License, Version 2.0. */
package androidx.fragment.app;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import org.qiyi.pluginlibrary.component.wraper.ActivityWrapper;

/** Wraps the host context while delegating host services to the original host. */
public final class PluginFragmentHostCallback extends FragmentHostCallback<FragmentActivity> {
    private final FragmentHostCallback<FragmentActivity> origin;
    private final ActivityWrapper wrapper;

    public PluginFragmentHostCallback(FragmentHostCallback<FragmentActivity> origin, ActivityWrapper wrapper) {
        super(wrapper, origin.getHandler(), 0);
        this.origin = origin;
        this.wrapper = wrapper;
    }
    @Override public FragmentActivity onGetHost() { return wrapper; }
    @Override public LayoutInflater onGetLayoutInflater() { return LayoutInflater.from(wrapper); }
    @Override public void onDump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) { origin.onDump(prefix, fd, writer, args); }
    @Override public boolean onShouldSaveFragmentState(Fragment fragment) { return origin.onShouldSaveFragmentState(fragment); }
    @Override public void onSupportInvalidateOptionsMenu() { origin.onSupportInvalidateOptionsMenu(); }
    @Override public void onStartActivityFromFragment(Fragment fragment, Intent intent, int requestCode, Bundle options) { super.onStartActivityFromFragment(fragment, intent, requestCode, options); }
    @Override public void onStartIntentSenderFromFragment(Fragment fragment, IntentSender intent, int requestCode, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, Bundle options) throws IntentSender.SendIntentException { super.onStartIntentSenderFromFragment(fragment, intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags, options); }
    @Override public void onRequestPermissionsFromFragment(Fragment fragment, String[] permissions, int requestCode) { origin.onRequestPermissionsFromFragment(fragment, permissions, requestCode); }
    @Override public boolean onShouldShowRequestPermissionRationale(String permission) { return origin.onShouldShowRequestPermissionRationale(permission); }
    @Override public boolean onHasWindowAnimations() { return origin.onHasWindowAnimations(); }
    @Override public int onGetWindowAnimations() { return origin.onGetWindowAnimations(); }
    @Override public View onFindViewById(int id) { return origin.onFindViewById(id); }
    @Override public boolean onHasView() { return origin.onHasView(); }
    @Override public Fragment instantiate(android.content.Context context, String name, Bundle args) { return origin.instantiate(context, name, args); }
}