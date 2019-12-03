package com.grass.mybaselib.vm;

/**
 * @author huchao
 * @time 2019/11/22
 * @describe
 * @package com.grass.mybaselib.model
 */
public class User2{


    public String name;
    public String pwd;

    public User2(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
