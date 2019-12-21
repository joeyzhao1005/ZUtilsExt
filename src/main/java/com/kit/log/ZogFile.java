/*
 * Copyright (c) 2018.
 * Author：Zhao
 * Email：joeyzhao1005@gmail.com
 */

package com.kit.log;

import android.content.Context;
import android.util.Log;

import androidx.annotation.StringDef;

import com.kit.app.application.AppMaster;
import com.kit.config.AppConfig;
import com.kit.utils.AppUtils;
import com.kit.utils.DateUtils;
import com.kit.utils.FileUtils;
import com.kit.utils.RxUtils;
import com.kit.utils.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author joeyzhao
 */
public final class ZogFile {
    /**
     * log默认存储目录
     */
    private static String defaultDir;

    /**
     * log存储目录
     */
    private static String dir;


    private static List<String> tagFilter;


    public static void setDir(final String dir) {
        if (StringUtils.isEmptyOrNullStr(dir)) {
            ZogFile.dir = null;
        } else {
            ZogFile.dir = dir.endsWith(File.separator) ? dir : dir + File.separator;
        }
    }

    public static void setDir(final File dir) {
        ZogFile.dir = dir == null ? null : dir.getAbsolutePath() + File.separator;
    }

    public static void setTagFilter(String... tagFilter) {
        ZogFile.tagFilter = Arrays.asList(tagFilter);
    }

    public static String getDir() {
        if (StringUtils.isEmptyOrNullStr(dir)) {
            defaultDir = AppMaster.getInstance().getAppContext().getExternalCacheDir() + File.separator + "logs";
            dir = defaultDir;
        }

        return dir;
    }

    public static String getLogFilePath() {
        return getDir() + File.separator + DateUtils.getCurrDateFormat("yyyyMMddHHmmss") + ".log";
    }

    public static void add(@Priority String priority, String tag, String logStr) {
        if (tagFilter == null || tagFilter.contains(tag)) {
            add(priority, "【" + tag + "】 " + logStr);
        }
    }

    public static void add(String logStr) {
        Log.e(INFO, logStr);
    }

    public static void add(@Priority String priority, String logStr) {
        AppConfig.IAppConfig appConfig = AppConfig.getAppConfig();
        if (appConfig == null || !appConfig.isShowLog()) {
            return;
        }

        if (!FileUtils.isExists(getDir())) {
            if (!FileUtils.mkDir(getDir())) {
                return;
            }
        }

        String filePath = getLogFilePath();
        File file = new File(filePath);
        boolean isCreated = true;
        if (!file.exists()) {
            try {
                isCreated = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!isCreated) {
            return;
        }

        RxUtils.io(null, new RxUtils.RxSimpleTask() {
            @Override
            public Object doSth(Object... objects) {
                try {
                    FileWriter writer = new FileWriter(filePath, true);
                    BufferedWriter bw = new BufferedWriter(writer);
                    String log = " 【" + DateUtils.getCurrDateFormat("HH:mm:ss") + "】 " + priority + " " + logStr + "\r\n";
                    bw.write(log);
                    bw.close();
                    writer.close();
                } catch (IOException e) {
                }

                return getDefault();
            }

            @Override
            public Object getDefault() {
                return "";
            }
        });
    }

    @StringDef({ERROR, INFO})
    @Retention(RetentionPolicy.SOURCE)
    @interface Priority {
    }

    public static final String ERROR = "ERROR";
    public static final String INFO = "INFO";

}