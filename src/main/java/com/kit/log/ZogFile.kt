/*
 * Copyright (c) 2018.
 * Author：Zhao
 * Email：joeyzhao1005@gmail.com
 */
package com.kit.log

import android.util.Log
import androidx.annotation.StringDef
import com.kit.app.application.AppMaster
import com.kit.config.AppConfig
import com.kit.utils.DateUtils
import com.kit.utils.FileUtils
import com.kit.utils.StringUtils
import com.kit.utils.log.Zog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.util.*

/**
 * @author joeyzhao
 */
object ZogFile {
    /**
     * log默认存储目录
     */
    private var defaultDir: String? = null
    /**
     * log存储目录
     */
    private var dir: String? = null
    private var tagFilter: List<String>? = null
    fun setDir(dir: String) {
        if (StringUtils.isEmptyOrNullStr(dir)) {
            ZogFile.dir = null
        } else {
            ZogFile.dir = if (dir.endsWith(File.separator)) dir else dir + File.separator
        }
    }

    fun setDir(dir: File?) {
        ZogFile.dir = if (dir == null) null else dir.absolutePath + File.separator
    }

    fun setTagFilter(vararg tagFilter: String?) {
        ZogFile.tagFilter = Arrays.asList<String>(*tagFilter)
    }

    fun getDir(): String? {
        if (StringUtils.isEmptyOrNullStr(dir)) {
            defaultDir = AppMaster.getInstance().appContext.externalCacheDir.toString() + File.separator + "logs"
            dir = defaultDir
        }
        return dir
    }

    @JvmStatic
    val logFilePath: String
        get() = getDir() + File.separator + DateUtils.getCurrDateFormat("yyyyMMdd") + ".log"

    fun add(@Priority priority: String?, tag: String, logStr: String) {
        if (tagFilter == null || tagFilter!!.contains(tag)) {
            add(priority, "【$tag】 $logStr")
        }
    }

    fun add(logStr: String) {
        Log.e(INFO, logStr)
    }

    fun add(@Priority priority: String?, logStr: String?) {
        GlobalScope.launch(Dispatchers.IO) {
            val appConfig = AppConfig.getAppConfig()
            if (appConfig == null || !appConfig.isShowLog) {
                return@launch
            }

            if (!FileUtils.isExists(getDir())) {
                if (!FileUtils.mkDir(getDir())) {
                    return@launch
                }
            }

            val filePath = logFilePath
            val file = File(filePath)
            var isCreated = true
            if (!file.exists()) {
                try {
                    isCreated = file.createNewFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            if (!isCreated && !file.exists()) {
                return@launch
            }

            try {
                val writer = FileWriter(filePath, true)
                val bw = BufferedWriter(writer)
                val log = " 【" + DateUtils.getCurrDateFormat("HH:mm:ss") + "】 " + priority + " " + logStr + "\r\n"
                bw.write(log)
                bw.close()
                writer.close()
            } catch (e: Exception) {
                Zog.showException(e)
            }

        }
    }

    const val ERROR = "ERROR"
    const val INFO = "INFO"

    @StringDef(ERROR, INFO)
    @Retention(RetentionPolicy.SOURCE)
    internal annotation class Priority
}