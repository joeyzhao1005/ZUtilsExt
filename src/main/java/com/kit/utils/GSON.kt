package com.kit.utils

import androidx.lifecycle.LifecycleCoroutineScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kit.app.Callback
import com.kit.thread.AppThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference
import java.lang.reflect.Type

/**
 * json解析类
 */
class GSON {
    private lateinit var gsonItem: Gson
    var jsonStr: String? = null
    var lifecycleProviderWeakReference: WeakReference<LifecycleCoroutineScope?>? = null
    var callbackInUI = false
    //////////to json string////////
    fun json(o: Any?): String {
        return if (o == null) {
            ""
        } else gsonItem.toJson(o)
    }

    fun json(o: Any?, callback: Callback<String?>?) {
        if (o == null) {
            callback?.call(null)
        }

        var scope = lifecycleProviderWeakReference?.get()
        if (scope != null) {
            scope.launch {
                var ret = gsonItem.toJson(o);


                if (callbackInUI) {
                    withContext(Dispatchers.Main) {
                        callback?.call(ret)
                    }
                } else {
                    callback?.call(ret)
                }

            }
        } else {
            AppThread.get().execute {
                var ret = gsonItem.toJson(o);

                if (callbackInUI) {
                    callback?.call(ret)
                }
            }
        }

    }

    //////////to json string////////
    fun data(jsonStr: String?): GSON {
        var jsonStr = jsonStr
        if (jsonStr == null) {
            jsonStr = ""
        }
        this.jsonStr = jsonStr
        return this
    }

    fun lifecycle(lifecycleProvider: LifecycleCoroutineScope?): GSON {
        lifecycleProviderWeakReference = WeakReference(lifecycleProvider)
        return this
    }

    /**
     * 仅异步调用有用
     *
     * @return
     */
    fun callbackInUI(): GSON {
        callbackInUI = true
        return this
    }

    /**
     * 异步获取返回结果
     *
     * @param callback
     */
    operator fun <T> get(clazz: Class<T>?, callback: Callback<T>?) {
        if (jsonStr == null || clazz == null) {
            callback?.call(null)
        }
        var scope = lifecycleProviderWeakReference?.get()
        if (scope != null) {
            scope.launch {
                var ret = gsonItem.fromJson<T>(jsonStr, clazz)

                if (callbackInUI) {
                    withContext(Dispatchers.Main) {
                        callback?.call(ret)
                    }
                } else {
                    callback?.call(ret)
                }

            }
        } else {
            AppThread.get().execute {
                var ret = gsonItem.fromJson<T>(jsonStr, clazz)

                if (callbackInUI) {
                    callback?.call(ret)
                }
            }
        }
    }

    /**
     * 异步获取返回结果
     *
     * @param callback
     */
    operator fun <T> get(typeOfT: Type?, callback: Callback<T>?) {
        if (jsonStr == null || typeOfT == null) {
            callback?.call(null)
        }
        var scope = lifecycleProviderWeakReference?.get()
        if (scope != null) {
            scope.launch {
                var ret = gsonItem.fromJson<T>(jsonStr, typeOfT)

                if (callbackInUI) {
                    withContext(Dispatchers.Main) {
                        callback?.call(ret)
                    }
                } else {
                    callback?.call(ret)
                }

            }
        } else {
            AppThread.get().execute {
                var ret = gsonItem.fromJson<T>(jsonStr, typeOfT)

                if (callbackInUI) {
                    callback?.call(ret)
                }
            }
        }
    }


    /**
     * 同步获取json对象
     *
     * @param clazz
     * @param <T>
     * @return
    </T> */
    fun <T> getSync(clazz: Class<T>?): T? {
        return if (jsonStr == null || clazz == null) {
            null
        } else gsonItem.fromJson(jsonStr, clazz)
    }

    /**
     * 同步获取json列表
     *
     * @param <T>
     * @return
    </T> */
    fun <T> getSync(typeOfT: Type?): T? {
        return if (jsonStr == null || typeOfT == null) {
            null
        } else gsonItem.fromJson(jsonStr, typeOfT)
    }

    companion object {
//        @JvmOverloads
//        fun create( lifecycleProvider: LifecycleCoroutineScope? = null): GSON {
//            return create(lifecycleProvider, false)
//        }

        @JvmOverloads
        fun create(lenient: Boolean = false): GSON {
            return create(null, lenient)
        }


        @JvmOverloads
        fun create(lifecycleProvider: LifecycleCoroutineScope?, lenient: Boolean = false): GSON {
            var builder = GsonBuilder()
            if (lenient) {
                builder = builder.setLenient()
            }
            val gsonItem = builder.create()
            val gson = GSON()
            gson.lifecycle(lifecycleProvider)
            gson.gsonItem = gsonItem
            return gson
        }
    }
}