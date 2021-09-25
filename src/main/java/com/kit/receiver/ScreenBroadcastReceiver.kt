package com.kit.receiver

import android.app.KeyguardManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.kit.utils.log.Zog
import com.kit.receiver.ScreenBroadcastReceiver
import android.content.IntentFilter
import android.os.Build
import com.kit.app.application.AppMaster
import com.kit.utils.ApiLevel

abstract class ScreenBroadcastReceiver : BroadcastReceiver() {
    private var action: String? = null
    override fun onReceive(context: Context, intent: Intent) {
        if (intent == null) {
            return
        }
        action = intent.action
        when {
            Intent.ACTION_SCREEN_ON == action -> {
                // 开屏
                Zog.i("开屏")
                onScreenOn(context, intent)
            }

            Intent.ACTION_SCREEN_OFF == action -> {
                // 熄屏
                Zog.i("熄屏")
                onScreenOff(context, intent)
            }

            Intent.ACTION_USER_PRESENT == action -> {
                // 解锁
                Zog.i("解锁")
                onScreenUnlock(context, intent)
            }
        }

//        else if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
//            // 当长按电源键弹出“关机”对话或者锁屏时系统会发出这个广播
//            Zog.i("锁屏");
//            onScreenLock(context,intent);
//
//            // example：有时候会用到系统对话框，权限可能很高，会覆盖在锁屏界面或者“关机”对话框之上，
//            // 所以监听这个广播，当收到时就隐藏自己的对话，如点击pad右下角部分弹出的对话框
//        }
    }

    open fun onScreenOn(context: Context?, intent: Intent?) {}
    open fun onScreenOff(context: Context?, intent: Intent?) {}
    open fun onScreenUnlock(context: Context?, intent: Intent?) {}


    fun attach(context: Context?): ScreenBroadcastReceiver {
        context?.let {
            registerScreenBroadcastReceiver(it, this@ScreenBroadcastReceiver)
        }
        return this
    }


    fun detach(context: Context?): ScreenBroadcastReceiver {
        context?.let {
            unregisterScreenBroadcastReceiver(it, this@ScreenBroadcastReceiver)
        }
        return this
    }

    private fun registerScreenBroadcastReceiver(
        context: Context,
        screenBroadcastReceiver: ScreenBroadcastReceiver?
    ) {
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        filter.addAction(Intent.ACTION_USER_PRESENT)
        try {
            context.registerReceiver(screenBroadcastReceiver, filter)
        } catch (e: IllegalArgumentException) {
            //empty
        } catch (e: Exception) {
            //empty
        }
    }


    private fun unregisterScreenBroadcastReceiver(
        context: Context,
        screenBroadcastReceiver: ScreenBroadcastReceiver?
    ) {
        try {
            context.unregisterReceiver(screenBroadcastReceiver)
        } catch (e: IllegalArgumentException) {
            //empty
        } catch (e: Exception) {
            //empty
        }
    }

    companion object {

        /**
         * 屏幕是否锁定
         */
        @JvmStatic
        fun isKeyguardLocked(): Boolean {
            val manager =
                AppMaster.getInstance().appContext.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager?
            return if (ApiLevel.ATLEAST_JELLY_BEAN) {
                manager?.isKeyguardLocked ?: false
            } else {
                return false
            }
        }
    }
}