package com.grass.mybaselib.network;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.grass.mybaselib.app.AppConfig;
import com.grass.parent.utils.SharePrefsUtils;

/**
 * @author huchao
 * @time 2019/12/1
 * @describe 开发服务器地址管理
 * @package com.grass.mybaselib.network
 */
public class UrlManager {
    public String baseUrl;

    //正式环境
    public static final String RELEASE = "https://wuliu.pinpianyi.com/";
    //alpha环境
    public static final String ALPHA = "https://wuliu.alpha.pinpianyi.cn/";
    //beta环境
    public static final String BETA = "https://wuliu.beta.pinpianyi.cn/";
    //开发环境
    public static final String TEST= "https://wuliu.test.pinpianyi.cn/";
    public static final String DEV = "https://wuliu.dev.pinpianyi.cn/";
    public static final String DEV2 = "https://wuliu.dev2.pinpianyi.cn/";
    public static final String DEV3 = "https://wuliu.dev3.pinpianyi.cn/";

    public String getBaseUrl() {
        String selUrl = SharePrefsUtils.getInstance().getString(AppConfig.Spkey.SELECTURL,"");
        if(TextUtils.isEmpty(selUrl)){
            baseUrl = DEV3;
        }else {
            baseUrl = selUrl;
        }
        return baseUrl;
    }
}
