package com.grass.mybaselib.vm;

import androidx.databinding.BaseObservable;
import androidx.databinding.library.baseAdapters.BR;

/**
 * @author huchao
 * @time 2019/11/22
 * @describe
 * @package com.grass.mybaselib.model
 */
public class User extends BaseObservable {


    public String name;
    public String pwd;

    public User(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
        notifyPropertyChanged(BR._all);
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR._all);
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
        notifyPropertyChanged(BR._all);
    }
}
