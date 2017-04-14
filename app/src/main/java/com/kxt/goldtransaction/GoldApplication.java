package com.kxt.goldtransaction;

import android.app.Application;

import com.socks.library.KLog;

/**
 * Created by Administrator on 2017/4/14.
 */

public class GoldApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        KLog.init(BuildConfig.LOG_DEBUG);
    }
}
