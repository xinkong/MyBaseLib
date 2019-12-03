package com.grass.mybaselib.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.grass.parent.base.AbsViewModel;
import com.grass.parent.base.BaseActivity;

import butterknife.ButterKnife;

public abstract class BaseTestActivity<VM extends AbsViewModel> extends BaseActivity<VM> {

    ButterKnife butterKnife;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        butterKnife.bind(this);
    }

}
