package com.grass.parent.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.grass.notchscreenlib.NotchScreenManager;
import com.grass.parent.R;
import com.grass.parent.bus.LiveBus;
import com.grass.parent.config.StateConstants;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author huchao
 * @time 2019/11/18
 * @describe
 * @package com.grass.mybaselib.base
 */
public abstract class BaseActivity<VM extends AbsViewModel> extends AppCompatActivity {

    LayoutInflater mLayoutInflater;

    public VM mViewModel;

    //标题栏部分
    Toolbar mToolbar;
    TextView mTvTitle;
    LinearLayout mLlLeftView;
    LinearLayout mLlMenus;

    //主体内容部分
    FrameLayout mFlContent;
    private List<Object> eventKeys = new ArrayList<>();

    //加载数据
    public FrameLayout mShowLoadingView;

    //错误部分
    FrameLayout mFlError;
    TextView mTvErrorMsg;
    Button mBtnRetry;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //保持竖屏
        if (isVerticalScreen()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        if(isFullScreen()){
            // 设置Activity全屏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            NotchScreenManager.getInstance().setDisplayInNotch(this);
        }
        setContentView(R.layout.base_view);
        //初始化公共组件
        initCommentView();
        //标题栏操作
        toolbarOperation();
        //页面主体内容操作
        contentOperation();
        dataObserver();
        pageLoadData();

    }



    protected abstract void dataObserver();


    protected <T> MutableLiveData<T> registerSubscriber(Object eventKey, Class<T> tClass) {

        return registerSubscriber(eventKey, null, tClass);
    }

    protected <T> MutableLiveData<T> registerSubscriber(Object eventKey, String tag, Class<T> tClass) {
        String event;
        if (TextUtils.isEmpty(tag)) {
            event = (String) eventKey;
        } else {
            event = eventKey + tag;
        }
        eventKeys.add(event);
        return LiveBus.getDefault().subscribe(eventKey, tag, tClass);
    }


    /**
     * 页面主体内容操作
     */
    protected void contentOperation() {
        View view = mLayoutInflater.inflate(getLayoutID(),mFlContent,false);
        mFlContent.addView(view);
        mViewModel = initViewModel();

        if (mViewModel != null) {
            mViewModel.mRepository.loadState.observe(this, observer);
        }
    }


    /**
     * 状态页面监听
     */
    protected Observer observer = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String state) {
            if (!TextUtils.isEmpty(state)) {
                switch (state) {
                    case StateConstants.SHOW_LOAD:
                        showLoadingView();
                        break;
                    case StateConstants.SERVER_SUCCESS:
                        hideLoadingView();
                        break;
                    case StateConstants.REQUESTER_ERROR:
                        showErrorView(false);
                        break;
                    case StateConstants.NO_NET_WORK_ERROR:
                        showErrorView(true);
                        break;
                }
            }
        }
    };

    protected void showErrorView(boolean isNoNet) {
        if(isNoNet){
            mTvErrorMsg.setText("请检查网络连接");
        }else {
            mTvErrorMsg.setText("访问异常,请重试");
        }
        hideLoadingView();
        mFlError.setVisibility(View.VISIBLE);
    }


    /**
     * 显示加载页
     */
    public void showLoadingView() {
        mShowLoadingView.setVisibility(View.VISIBLE);
        mFlError.setVisibility(View.GONE);
    }

    public void hideLoadingView() {
        mShowLoadingView.setVisibility(View.GONE);
        mFlError.setVisibility(View.GONE);
    }

    protected abstract VM initViewModel();

    //获得资源ID
    protected abstract int getLayoutID();

    //页面加载数据
    protected abstract void pageLoadData();

    //重试
    public void onRetry() {
        pageLoadData();
    }

    /**
     * 标题栏操作
     */
    protected void toolbarOperation() {
        if (isShowTitle()) {
            if (isCanBack()) {
                mLlLeftView.setOnClickListener(v -> onLeftClick());
            } else {
                mLlLeftView.setVisibility(View.GONE);
            }
        } else {
            mToolbar.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化公共组件
     */
    private void initCommentView() {
        mLayoutInflater = LayoutInflater.from(this);

        mToolbar = findViewById(R.id.base_toolbar);
        mTvTitle = findViewById(R.id.base_toolbar_title);
        mLlLeftView = findViewById(R.id.base_left_view);
        mLlMenus = findViewById(R.id.base_menu_info);

        mFlContent = findViewById(R.id.base_content);

        mShowLoadingView = findViewById(R.id.layout_loding);

        //错误类容
        mFlError = findViewById(R.id.layout_error);
        mTvErrorMsg = findViewById(R.id.error_tv_msg);
        mBtnRetry = findViewById(R.id.error_btn_retry);
        //重试
        RxView.clicks(mBtnRetry)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(v -> onRetry());

    }

    /**
     * 设置标题
     */
    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    /**
     * 是否包含标题栏
     *
     * @return
     */
    public boolean isShowTitle() {
        return true;
    }

    /**
     * 页面是否可放回
     *
     * @return
     */
    public boolean isCanBack() {
        return true;
    }

    /**
     * 设置菜单 给出一个资源ID
     *
     * @param menuId
     */
    public void setMenu(int menuId) {

        View view = mLayoutInflater.inflate(menuId, null);
        if (view instanceof ViewGroup) {
            new RuntimeException("menu must be ViewGroup");
        }
        mLlMenus.setVisibility(View.VISIBLE);
        ViewGroup menu = (ViewGroup) view;
        for (int i = 0; i < menu.getChildCount(); i++) {
            View item = menu.getChildAt(i);
            item.setTag(i);
            RxView.clicks(item)
                    .throttleFirst(2, TimeUnit.SECONDS)//2秒钟内只允许点击1次
                    .subscribe(v -> {
                        onMenuItemClick((Integer) item.getTag());
                    });
        }
        mLlMenus.addView(view);
    }

    /**
     * 左侧按钮点击
     */
    public void onLeftClick() {
        onBackPressed();
    }


    /**
     * 设置左侧控件
     */
    public void setLeftView(int layoutId) {
        mLlLeftView.removeAllViews();
        View view = mLayoutInflater.inflate(layoutId, mLlLeftView, false);
        mLlLeftView.addView(view);
    }

    public void onMenuItemClick(int position) {
    }

    /**
     * 界面是否是竖屏显示
     *
     * @return true 默认竖屏 false 横屏
     */
    public boolean isVerticalScreen() {
        return true;
    }

    /**
     * 是否是全屏显示
     * @return true 全屏 false 不是
     */
    protected boolean isFullScreen(){
        return false;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearEvent();
//        if (mBinding != null) {
//            mBinding.unbind();
//        }
    }

    protected void clearEvent() {
        for (Object eventKey : eventKeys) {
            LiveBus.getDefault().clear(eventKey);
        }
    }
}
