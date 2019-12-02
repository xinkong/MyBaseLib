package com.grass.mybaselib.rep;

import android.util.Log;

import com.grass.mybaselib.data.BaseRepository;
import com.grass.mybaselib.data.bean.Detail;
import com.grass.mybaselib.network.HttpCurrencyParams;
import com.grass.mybaselib.network.rx.RxCommonResult;
import com.grass.parent.http.rx.RxSubscriber;
import com.grass.mybaselib.vm.User;
import com.grass.parent.bus.LiveBus;
import com.grass.parent.http.rx.RxSchedulers;
import com.grass.parent.utils.OnlyKeyUtil;

/**
 * @author huchao
 * @time 2019/11/26
 * @describe
 * @package com.grass.mybaselib.rep
 */
public class MainRepository extends BaseRepository {

    public static String EVENT_KEY_HOME;

    public MainRepository() {
//        if (EVENT_KEY_HOME == null) {
            EVENT_KEY_HOME = OnlyKeyUtil.getEventKey();
//        }
    }

    public void getUpdateInfo() {
        addDisposable(
                apiService.getUpdateInfo(HttpCurrencyParams.getPostParams())
                        .compose(RxSchedulers.io_main())
                        .compose(RxCommonResult.handleResultData())
                        .subscribeWith(new RxSubscriber<Detail>(loadState) {
                            @Override
                            public void onSuccess(Detail s) {
                                Log.i("tag", s.toString());
                            }
                        })
        );
    }
    public void getUpdateInfo2() {
        addDisposable(
                apiService.getUpdateInfo2()
                        .compose(RxSchedulers.io_main())
                        .compose(RxCommonResult.handleResultData())
                        .subscribeWith(new RxSubscriber<Detail>(loadState,false,false) {
                            @Override
                            public void onSuccess(Detail s) {
                                Log.i("tag", s.toString());
                            }
                        })
        );
    }

    public void getData() {
        LiveBus.getDefault().postEvent(EVENT_KEY_HOME, null, new User("ada", "xxx"));
    }
    public void getData2() {
        LiveBus.getDefault().postEvent(EVENT_KEY_HOME, null, new User("第二个界面", "第二个界面"));
    }
}
