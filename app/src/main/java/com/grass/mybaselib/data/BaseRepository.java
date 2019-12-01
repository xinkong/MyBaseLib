package com.grass.mybaselib.data;

import com.grass.mybaselib.network.ApiService;
import com.grass.parent.base.AbsRepository;
import com.grass.parent.bus.LiveBus;
import com.grass.parent.http.HttpHelper;


/**
 * @author：tqzhang on 18/7/26 16:15
 */
public class BaseRepository extends AbsRepository {

    protected ApiService apiService;

    public BaseRepository() {
        if (null == apiService) {
            apiService = HttpHelper.getInstance().create(ApiService.class);
        }
    }


    protected void postData(Object eventKey, Object t) {
        postData(eventKey, null, t);
    }


    protected void showPageState(Object eventKey, String state) {
        postData(eventKey, state);
    }

    protected void showPageState(Object eventKey, String tag, String state) {
        postData(eventKey, tag, state);
    }

    protected void postData(Object eventKey, String tag, Object t) {
        LiveBus.getDefault().postEvent(eventKey, tag, t);
    }

}
