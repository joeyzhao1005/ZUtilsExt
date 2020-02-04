package com.kit.ui.base

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


/**
 * 只需要在oncreate上添加即可<br>
 * 绑定activity与协程Scope，尽量减少修改。</br>
 * 注意：不要在onDestroy里启动协程</br>
 * 使用方法：<br>
 * ```
 * override fun onCreate(savedInstanceState: Bundle?) = installCoroutineScope(savedInstanceState){
 *      super.onCreate(savedInstanceState)
 *      setContentView(R.layout.activity_main)
 * }
 * ```
 */
fun installCoroutineScope(savedInstanceState: Bundle?, onCreate: (savedInstanceState: Bundle?) -> Unit) {
    onCreate(savedInstanceState)
}


fun LifecycleOwner.launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
): Job {
    return lifecycleScope.launch(context, start, block)
}

fun LifecycleOwner.launchMain(
        context: CoroutineContext = EmptyCoroutineContext + Dispatchers.Main,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
): Job {
    return lifecycleScope.launch(context, start, block)
}

fun LifecycleOwner.launchIO(
        context: CoroutineContext = EmptyCoroutineContext + Dispatchers.IO,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
): Job {
    return lifecycleScope.launch(context, start, block)
}

fun <T> LifecycleOwner.async(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> T
): Deferred<T> {
    return lifecycleScope.async(context, start, block)
}

fun <T> LifecycleOwner.asyncIO(
        context: CoroutineContext = EmptyCoroutineContext + Dispatchers.IO,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> T
): Deferred<T> {
    return lifecycleScope.async(context, start, block)
}