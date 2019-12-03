package com.grass.mybaselib.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.grass.mybaselib.R;
import com.grass.mybaselib.base.BaseTestActivity;
import com.grass.mybaselib.rep.MainRepository;
import com.grass.mybaselib.vm.User;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseTestActivity<MainMiewModel> {

    @BindView(R.id.tv_name)
    TextView tv_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("测试界面");
        setMenu(R.layout.menu_add);
    }

    @Override
    protected void dataObserver() {

//
        registerSubscriber(MainRepository.EVENT_KEY_HOME, List.class).observe(this, list -> {
            User user = (User) list.get(0);
//            mBinding.setViewmodel(user);
            tv_name.setText(user.name);
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
        return true;
    }

    @Override
    protected boolean isFullScreen() {
        return true;
    }
}
