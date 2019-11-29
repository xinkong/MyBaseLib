package com.grass.parent.init;

import android.app.Application;

/**
 * @author huchao
 * @time 2019/11/24
 * @describe
 * @package com.grass.mybaselib.base
 */
public class BaseModuleInit implements IModuelInit {
    public static Application mApplication;

    @Override
    public void init(Application application) {
        this.mApplication = application;
    }

    public static Application getApplication(){
        return mApplication;
    }
}
