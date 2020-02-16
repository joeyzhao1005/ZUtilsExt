/*
 * Copyright (c) 2018.
 * Author：Zhao
 * Email：joeyzhao1005@gmail.com
 */

package com.kit.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.kit.app.application.AppMaster;
import com.kit.utils.StringUtils;
import com.kit.utils.log.Zog;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author joeyzhao
 */
public class ProcessBroadcastCenter {

    private Intent intent;
    private String action;
    private Bundle data;
    private boolean isUsing = false;

    private static final Set<ProcessBroadcastCenter> BROADCAST_CENTER_POOL = new CopyOnWriteArraySet<>();

    private static final int MAX = 5;
    private WeakReference<Context> contextWeakReference;


    private static synchronized ProcessBroadcastCenter createNew(Context context) {
        ProcessBroadcastCenter broadcastCenter = new ProcessBroadcastCenter();
        broadcastCenter.isUsing = true;
        broadcastCenter.contextWeakReference = new WeakReference<>(context);
        BROADCAST_CENTER_POOL.add(broadcastCenter);


        //池中大于MAX 移除一个没在使用的
        if (BROADCAST_CENTER_POOL.size() > MAX) {
            for (ProcessBroadcastCenter bc : BROADCAST_CENTER_POOL) {
                if (!bc.isUsing) {
                    BROADCAST_CENTER_POOL.remove(bc);
                    break;
                }
            }
        }
        return broadcastCenter;
    }

    public static synchronized ProcessBroadcastCenter get(Context context) {
        if (BROADCAST_CENTER_POOL.isEmpty()) {
            return createNew(context);
        } else {
            ProcessBroadcastCenter getOne = null;
            for (ProcessBroadcastCenter broadcastCenter : BROADCAST_CENTER_POOL) {
                if (!broadcastCenter.isUsing) {
                    getOne = broadcastCenter;
                    break;
                }
            }

            if (getOne == null) {
                return createNew(context);
            } else {
                getOne.reset();
                getOne.isUsing = true;
                getOne.contextWeakReference = new WeakReference<>(context);

                return getOne;
            }
        }

    }


    public void reset() {
        contextWeakReference = null;
        intent = null;
        action = null;
        data = null;
        isUsing = false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    public ProcessBroadcastCenter intent(Intent intent) {
        this.intent = intent;
        return this;
    }


    public ProcessBroadcastCenter action(String action) {
        createIntent();
        if (intent == null) {
            Zog.e("intent create failed");
            return this;
        }

        this.action = action;
        intent.setAction(action);
        return this;
    }


    public ProcessBroadcastCenter extras(Bundle bundle) {
        createIntent();

        if (intent == null) {
            Zog.e("intent create failed");
            return this;
        }
        intent.putExtras(bundle);
        return this;
    }


    public ProcessBroadcastCenter put(String key, ArrayList<? extends Parcelable> value) {
        createIntent();

        if (intent == null) {
            Zog.e("intent create failed");
            return this;
        }

        intent.putExtra(key, value);
        return this;
    }

    public ProcessBroadcastCenter put(String key, Parcelable[] value) {
        createIntent();

        if (intent == null) {
            Zog.e("intent create failed");
            return this;
        }

        intent.putExtra(key, value);
        return this;
    }


    public ProcessBroadcastCenter put(String key, Parcelable value) {
        createIntent();

        if (intent == null) {
            Zog.e("intent create failed");
            return this;
        }

        intent.putExtra(key, value);
        return this;
    }

    public ProcessBroadcastCenter put(String key, float value) {
        createIntent();

        if (intent == null) {
            Zog.e("intent create failed");
            return this;
        }

        intent.putExtra(key, value);
        return this;
    }

    public ProcessBroadcastCenter put(String key, double value) {
        createIntent();

        if (intent == null) {
            Zog.e("intent create failed");
            return this;
        }

        intent.putExtra(key, value);
        return this;
    }

    public ProcessBroadcastCenter put(String key, long value) {
        createIntent();

        if (intent == null) {
            Zog.e("intent create failed");
            return this;
        }

        intent.putExtra(key, value);
        return this;
    }

    public ProcessBroadcastCenter put(String key, boolean value) {
        createIntent();

        if (intent == null) {
            Zog.e("intent create failed");
            return this;
        }

        intent.putExtra(key, value);
        return this;
    }

    public ProcessBroadcastCenter put(String key, int value) {
        createIntent();

        if (intent == null) {
            Zog.e("intent create failed");
            return this;
        }

        intent.putExtra(key, value);
        return this;
    }


    public ProcessBroadcastCenter put(String key, String str) {
        createIntent();

        if (intent == null) {
            Zog.e("intent create failed");
            return this;
        }

        intent.putExtra(key, str);
        return this;
    }

    private void createIntent() {

//        if (intent == null) {
//            Zog.d("intent is not created");
//        }

        if (intent == null) {
            if (!StringUtils.isEmptyOrNullStr(action)) {
                intent = new Intent(action);
            } else {
                intent = new Intent();
            }
        }

    }


    public void broadcast() {

        createIntent();

        if (intent == null) {
            Zog.e("intent create failed");
            isUsing = false;
            return;
        }

        if (action == null) {
            Zog.e("action is null!!!");
            isUsing = false;
            return;
        }

        if (data != null) {
            intent.putExtra(action, data);
        }

        intent.setAction(action);

        Context context = contextWeakReference.get();
        if (null != context) {
            context.sendBroadcast(intent);
        } else {
            AppMaster.getInstance().getAppContext().sendBroadcast(intent);
        }
        isUsing = false;
    }

    public static void registerReceiver(Context context, BroadcastReceiver br, List<String> actions) {
        if (null == br || null == actions) {
            Zog.e("registerReceiver | param is null ");
            return;
        }

        IntentFilter iFilter = new IntentFilter();
        for (String action : actions) {
            iFilter.addAction(action);
        }
        if (null != context) {
            context.registerReceiver(br, iFilter);
        } else {
            AppMaster.getInstance().getAppContext().registerReceiver(br, iFilter);
        }
    }


    public static void registerReceiver(Context context, BroadcastReceiver br, String... actions) {
        if (actions == null || actions.length <= 0) {
            return;
        }
        registerReceiver(context, br, Arrays.asList(actions));
    }


    /**
     * @param br
     */
    public static void unregisterReceiver(Context context, BroadcastReceiver br) {
        if (null == br) {
            Zog.e("unregisterReceiver | param is null");
            return;
        }

        try {
            if (null != context) {
                context.unregisterReceiver(br);
            } else {
                AppMaster.getInstance().getAppContext().unregisterReceiver(br);
            }
        } catch (Exception e) {

        }
    }


}

