package com.grass.mybaselib.ui;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.grass.mybaselib.BR;
import com.grass.mybaselib.R;
import com.grass.mybaselib.databinding.ActivityMain2Binding;
import com.grass.mybaselib.vm.User;
import com.grass.mybaselib.rep.Main2Repository;
import com.grass.mybaselib.rep.MainRepository;
import com.grass.parent.base.BaseActivity;

public class MainActivity extends BaseActivity<ActivityMain2Binding, MainMiewModel> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("测试界面");
        setMenu(R.layout.menu_add);

//        mBinding.setUser(u);
        mViewModel.getData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewModel.getData2();
            }
        },5000);
    }

    @Override
    protected void dataObserver() {
        registerSubscriber(MainRepository.EVENT_KEY_HOME, User.class)
                .observe(this, user -> {
                    if (user != null) {
                        mBinding.setUser(user);
                    }
                });

        registerSubscriber(Main2Repository.EVENT_KEY_HOME, User.class)
                .observe(this, user -> {
                    if (user != null) {
                        mBinding.setUser(user);
                    }
                });
    }

    @Override
    protected MainMiewModel initViewModel() {
        return ViewModelProviders.of(this).get(MainMiewModel.class);
    }


    @Override
    protected int initVariableId() {
        return BR.user;
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
