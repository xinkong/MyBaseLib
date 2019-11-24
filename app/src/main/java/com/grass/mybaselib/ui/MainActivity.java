package com.grass.mybaselib.ui;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.grass.mybaselib.BR;
import com.grass.mybaselib.R;
import com.grass.mybaselib.model.User;
import com.grass.parent.base.BaseActivity;

public class MainActivity extends BaseActivity {
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("测试界面");
        setMenu(R.layout.menu_add);
        user = new User("张三", "123");
        mBinding .setVariable(BR.user,user);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                user.setName("李四");
                user.pwd = "ddd";
            }
        }, 3000);


    }

    @Override
    public void onMenuItemClick(int position) {
        super.onMenuItemClick(position);
        Toast.makeText(this,position+"",Toast.LENGTH_SHORT).show();
        Log.i("tag",position+"");
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main2;
    }

}
