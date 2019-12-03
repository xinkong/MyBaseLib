package com.grass.mybaselib.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.grass.mybaselib.BR;
import com.grass.mybaselib.R;
import com.grass.mybaselib.databinding.ActivityMain2Binding;
import com.grass.mybaselib.rep.MainRepository;
import com.grass.mybaselib.vm.User;
import com.grass.parent.base.BaseActivity;

import java.util.List;

public class MainActivity extends BaseActivity<ActivityMain2Binding, MainMiewModel> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("测试界面");
        setMenu(R.layout.menu_add);
    }

    @Override
    protected void dataObserver() {
//        registerSubscriber(MainRepository.EVENT_KEY_HOME, User.class)
//                .observe(this, user -> {
//                    if (user != null) {
//                        mBinding.setUser(user);
//                    }
//                });

        registerSubscriber(MainRepository.EVENT_KEY_HOME, List.class).observe(this, list -> {
            User user = (User) list.get(0);
            mBinding.setUser(user);
        });

    }

    public void testNet(View view){
        mViewModel.getUpdateInfo();
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
        if(position == 1){
            startActivity(new Intent(this,SecondActivity.class));
        }
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main2;
    }

    @Override
    protected void pageLoadData() {
        mViewModel.getData();
//        mViewModel.getUpdateInfo();
    }

    @Override
    public boolean isShowTitle() {
        return false;
    }

    @Override
    protected boolean isFullScreen() {
        return true;
    }
}
