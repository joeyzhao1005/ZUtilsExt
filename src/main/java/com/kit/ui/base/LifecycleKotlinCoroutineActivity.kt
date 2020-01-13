package com.kit.ui.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

open class LifecycleKotlinCoroutineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) = installCoroutineScope(savedInstanceState) {
        super.onCreate(savedInstanceState)
    }


    /***
     * 点击事件的View扩展
     * @param block: (T) -> Unit 函数
     * @return Unit
     */
    fun <T : View> T.click(block: (T) -> Unit) = setOnClickListener {
        block(it as T)
    }

    /***
     * 带延迟过滤的点击事件View扩展
     * @param delay Long 延迟时间，默认600毫秒
     * @param block: (T) -> Unit 函数
     * @return Unit
     */
    fun <T : View> T.clickWithQuickCheck(time: Int = 600, block: (T) -> Unit) {
        triggerDelay = time
        setOnClickListener {
            if (clickEnable()) {
                block(it as T)
            }
        }
    }


    private fun <T : View> T.clickEnable(): Boolean {
        var flag = false
        val currentClickTime = System.currentTimeMillis()
        if (currentClickTime - triggerLastTime >= triggerDelay) {
            flag = true
        }
        triggerLastTime = currentClickTime
        return flag
    }

    private var triggerDelay: Int = 600

    private var triggerLastTime: Long = 0L


}

