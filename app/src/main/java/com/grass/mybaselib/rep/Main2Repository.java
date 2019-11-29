package com.grass.mybaselib.rep;

import com.grass.mybaselib.vm.User;
import com.grass.parent.base.AbsRepository;
import com.grass.parent.bus.LiveBus;

import java.util.UUID;

/**
 * @author huchao
 * @time 2019/11/26
 * @describe
 * @package com.grass.mybaselib.rep
 */
public class Main2Repository extends AbsRepository {

    public static final String EVENT_KEY_HOME = UUID.randomUUID().toString();


    public void getData(){
//        addDisposable();
        LiveBus.getDefault().postEvent(EVENT_KEY_HOME,null,new User("ada","xxx"));
    }
    public void getData2(){
//        addDisposable();
        LiveBus.getDefault().postEvent(EVENT_KEY_HOME,null,new User("xxxxx","asdfasd"));
    }
}
