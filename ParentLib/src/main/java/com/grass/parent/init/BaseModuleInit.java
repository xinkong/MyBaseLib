package com.grass.parent.init;

import android.app.Application;

import com.grass.parent.utils.TUtil;

/**
 * @author huchao
 * @time 2019/11/24
 * @describe
 * @package 初始化上下文Context
 */
public class BaseModuleInit {
    public static Application mApplication;

    public static void init(Application application) {
        mApplication = application;
    }

    public static Application getApplication() {
        TUtil.checkNotNull(mApplication);
        return mApplication;
    }
}
