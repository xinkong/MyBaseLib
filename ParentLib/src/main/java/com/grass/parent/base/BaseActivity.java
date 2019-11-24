package com.grass.parent.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.grass.parent.R;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.concurrent.TimeUnit;

/**
 * @author huchao
 * @time 2019/11/18
 * @describe
 * @package com.grass.mybaselib.base
 */
public abstract class BaseActivity extends AppCompatActivity {

    LayoutInflater mLayoutInflater;

    //标题栏部分
    Toolbar mToolbar;
    TextView mTvTitle;
    LinearLayout mLlLeftView;
    LinearLayout mLlMenus;

    //主体内容部分
    FrameLayout mFlContent;
    public ViewDataBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //保持竖屏
        if(isVerticalScreen()){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        setContentView(R.layout.base_view);
        //初始化公共组件
        initCommentView();
        //标题栏操作
        toolbarOperation();
        //页面主体内容操作
        contentOperation();
    }

    /**
     * 页面主体内容操作
     */
    protected void contentOperation(){
        mBinding = DataBindingUtil.inflate(mLayoutInflater,getLayoutID(),mFlContent,false);
        mFlContent.addView(mBinding.getRoot());
    }

    //获得资源ID
    protected abstract int getLayoutID();

    /**
     * 标题栏操作
     */
    protected  void toolbarOperation(){
        if(isShowTitle()){
            if(isCanBack()){
                mLlLeftView.setOnClickListener(v -> onBackPressed());
            }else {
                mLlLeftView.setVisibility(View.GONE);
            }
        }else {
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

    }

    /**
     * 设置标题
     */
    public void setTitle(String title){
        mTvTitle.setText(title);
    }

    /**
     * 是否包含标题栏
     * @return
     */
    public boolean isShowTitle(){
        return true;
    }

    /**
     * 页面是否可放回
     * @return
     */
    public boolean isCanBack(){
        return true;
    }

    /**
     * 设置菜单 给出一个资源ID
     * @param menuId
     */
    public void setMenu(int menuId){

        View view = mLayoutInflater.inflate(menuId, null);
        if(view instanceof ViewGroup){
            new RuntimeException("餐单更结点必须是ViewGroup");
        }
        mLlMenus.setVisibility(View.VISIBLE);
        ViewGroup menu = (ViewGroup) view;
        for (int i = 0; i < menu.getChildCount(); i++) {
            View item = menu.getChildAt(i);
            item.setTag(i);
            RxView.clicks(item)
                    .throttleFirst(2, TimeUnit.SECONDS)//2秒钟内只允许点击1次
                    .subscribe(v ->{
                        onMenuItemClick((Integer) item.getTag());
                    });
        }
        mLlMenus.addView(view);
    }

    /**
     * 设置左侧控件
     */
    public void setLeftView(int layoutId){
        mLlLeftView.removeAllViews();
        View view = mLayoutInflater.inflate(layoutId, mLlLeftView, false);
        mLlLeftView.addView(view);
    }

    public void onMenuItemClick(int position){}
    /**
     * 界面是否是竖屏显示
     * @return true 默认竖屏 false 横屏
     */
    public boolean isVerticalScreen(){
        return true;
    }

}
