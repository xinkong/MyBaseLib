package com.grass.mybaselib.app;

import android.app.Application;

import com.grass.mybaselib.network.UrlManager;
import com.grass.parent.http.HttpHelper;
import com.grass.parent.init.BaseModuleInit;

/**
 * @author huchao
 * @time 2019/12/1
 * @describe
 * @package com.grass.mybaselib.app
 */
public class BaseApplication extends Application {

    public static Application mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        initAppParams();
    }

    private void initAppParams() {
        BaseModuleInit.init(this);
        new HttpHelper.Builder(this)
                .initOkHttp()
                .createRetrofit(new UrlManager().getBaseUrl())
                .build();
    }


}
