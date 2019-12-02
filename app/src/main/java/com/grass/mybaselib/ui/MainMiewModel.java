package com.grass.mybaselib.ui;

import android.app.Application;

import androidx.annotation.NonNull;

import com.grass.mybaselib.rep.MainRepository;
import com.grass.parent.base.AbsViewModel;

/**
 * @author huchao
 * @time 2019/11/25
 * @describe
 * @package com.grass.mybaselib.ui
 */
public class MainMiewModel extends AbsViewModel<MainRepository> {

    public MainMiewModel(@NonNull Application application) {
        super(application);
    }

    public void getData(){
        mRepository.getData();
    }

    public void getData2(){
        mRepository.getData2();
    }

    public void getUpdateInfo(){
        mRepository.getUpdateInfo();
    }
    public void getUpdateInfo2(){
        mRepository.getUpdateInfo2();
    }
}
