package com.grass.mybaselib.rep;

import android.util.Log;

import com.grass.mybaselib.data.BaseRepository;
import com.grass.mybaselib.network.rx.RxSubscriber;
import com.grass.mybaselib.vm.User;
import com.grass.parent.base.AbsRepository;
import com.grass.parent.bus.LiveBus;
import com.grass.parent.http.rx.RxSchedulers;
import com.grass.parent.utils.OnlyKeyUtil;

import java.util.UUID;

/**
 * @author huchao
 * @time 2019/11/26
 * @describe
 * @package com.grass.mybaselib.rep
 */
public class MainRepository extends BaseRepository {

    public static String EVENT_KEY_HOME;

    public MainRepository() {
        if(EVENT_KEY_HOME == null){
            EVENT_KEY_HOME = OnlyKeyUtil.getEventKey();
        }
    }

    public void getUpdateInfo(){

        addDisposable(

        apiService.getUpdateInfo()
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Log.i("tag",s);
                    }

                    @Override
                    public void onFailure(String msg, int code) {

                    }
                })
        );
    }

    public void getData(){
        LiveBus.getDefault().postEvent(EVENT_KEY_HOME,null,new User("ada","xxx"));
    }
}
