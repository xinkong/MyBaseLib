package com.grass.mybaselib.network;

import com.grass.parent.init.BaseModuleInit;
import com.grass.parent.utils.Tools;

import java.util.HashMap;
import java.util.Map;

public class HttpCurrencyParams {
    public static Map<String,Object> getPostParams (){
        return getPostParams(null);
    }

    public static Map<String,Object> getPostParams (Object params){
        Map<String,Object>  head = new HashMap<>();
        head.put ("channelId", "");
        head.put ("platform", "Android");
        head.put ("version", Tools.getVersionName(BaseModuleInit.getApplication()) + "");

        head.put("token","f006a047-f5a7-4b02-8e92-739a2db3385f");
        head.put("userId",5577);
        Map<String,Object> common = new HashMap<>();
        common.put("head",head);
        if(params!=null){
            common.put("body",params);
        }else {
            common.put("body",new HashMap<>());
        }
        return common;
    }
}
