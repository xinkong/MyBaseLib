package com.grass.mybaselib.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;

import com.grass.mybaselib.BR;
import com.grass.mybaselib.R;
import com.grass.mybaselib.databinding.ActivityMain3Binding;
import com.grass.mybaselib.rep.MainRepository;
import com.grass.mybaselib.vm.User;
import com.grass.parent.base.AbsViewModel;
import com.grass.parent.base.BaseActivity;

public class SecondActivity extends BaseActivity<ActivityMain3Binding,MainMiewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("第二个界面");

    }

    @Override
    protected void dataObserver() {
        registerSubscriber(MainRepository.EVENT_KEY_HOME, User.class)
                .observe(this, user -> {
                    if (user != null) {
                        mBinding.setUser(user);
                    }
                });
    }

    @Override
    public boolean isVerticalScreen() {
        return false;
    }

    public void testNet2(View view){
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
    protected int getLayoutID() {
        return R.layout.activity_main3;
    }

    @Override
    protected void pageLoadData() {
        mViewModel.getUpdateInfo2();
    }
}
