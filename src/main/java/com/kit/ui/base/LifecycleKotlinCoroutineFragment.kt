package com.kit.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.kit.utils.ApiLevel
import com.kit.utils.DensityUtils

/**
 * @author Zhao
 *
 *
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [BaseFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the newInstance() factory method to
 * create an instance of this fragment.
 */
open class LifecycleKotlinCoroutineFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) = installCoroutineScope(savedInstanceState) {
        super.onCreate(savedInstanceState)
    }


    /***
     * showContextMenu
     * @param delay Long 延迟时间，默认600毫秒
     * @param block: (T) -> Unit 函数
     * @return Unit
     */
    fun <T : View> T.showContextMenuCompat() {
        if (ApiLevel.ATLEAST_N) {
            showContextMenu((width / 2 - DensityUtils.dip2px(200) / 2).toFloat(), (0).toFloat())
        } else {
            showContextMenu()
        }
    }

    /***
     * 点击事件的View扩展
     * @param block: (T) -> Unit 函数
     * @return Unit
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : View> T.click(block: (T) -> Unit) = setOnClickListener {
        block(it as T)
    }


    /***
     * 带延迟过滤的点击事件View扩展
     * @param delay Long 延迟时间，默认600毫秒
     * @param block: (T) -> Unit 函数
     * @return Unit
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : View> T.clickWithQuickCheck(time: Int = 600, block: (T) -> Unit) {
        triggerDelay = time
        setOnClickListener {
            if (clickEnable()) {
                block(it as T)
            }
        }
    }


    /***
     * 带延迟过滤的点击事件View扩展
     * @param delay Long 延迟时间，默认600毫秒
     * @param block: (T) -> Unit 函数
     * @return Unit
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : View> T.doubleClick(time: Int = 600, block: (T) -> Unit) {
        triggerDelay = time
        setOnClickListener {
            val currentClickTime = System.currentTimeMillis()
            if (currentClickTime - doubleClickLastTime < triggerDelay) {
                block(it as T)
            }
            doubleClickLastTime = currentClickTime
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

    private var doubleClickLastTime: Long = 600

    private var triggerDelay: Int = 600

    private var triggerLastTime: Long = 0L

}