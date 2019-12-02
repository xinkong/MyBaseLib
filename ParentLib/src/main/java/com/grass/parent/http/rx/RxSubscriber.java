package com.grass.parent.http.rx;


import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonParseException;
import com.grass.parent.config.StateConstants;
import com.grass.parent.http.ServerException;
import com.grass.parent.utils.NetworkUtils;
import com.grass.parent.utils.ToastUtils;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.HttpException;

/**
 *
 */
public abstract class RxSubscriber<T> extends DisposableSubscriber<T> {

    private boolean isShowProgress = false;
    private boolean isDefaultShowLoadPage = true;
    public MutableLiveData<String> mLoadState;

    public RxSubscriber(MutableLiveData<String> loadState) {
        this(loadState, false);
    }

    public RxSubscriber(MutableLiveData<String> loadState, boolean isShowProgress) {
        this(loadState, isShowProgress, true);
    }

    public RxSubscriber(MutableLiveData<String> loadState, boolean isShowProgress, boolean isDefaultShowLoadPage) {
        super();
        this.isShowProgress = isShowProgress;
        this.mLoadState = loadState;
        this.isDefaultShowLoadPage = isDefaultShowLoadPage;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isDefaultShowLoadPage) {
            showLoading();
        }
        if (!NetworkUtils.isNetworkAvailable()) {
            onNoNetWork();
            cancel();
            ToastUtils.showToast("网络状态异常");
            return;
        }
    }


    @Override
    public void onComplete() {

    }

    protected void showLoading() {
        if (mLoadState != null) {
            if (isShowProgress) {
                mLoadState.postValue(StateConstants.SHOW_DIALOG);
            } else {
                mLoadState.postValue(StateConstants.SHOW_LOAD);
            }
        }
    }

    protected void onNoNetWork() {
        if (mLoadState != null) {
            mLoadState.postValue(StateConstants.NO_NET_WORK_ERROR);
        }
    }

    @Override
    public void onError(Throwable e) {
        String message;
        int code = -1;
        if (e instanceof UnknownHostException) {
            message = "没有网络";
        } else if (e instanceof HttpException) {
            message = "网络错误";
        } else if (e instanceof SocketTimeoutException) {
            message = "网络连接超时";
        } else if (e instanceof JsonParseException
                || e instanceof JSONException) {
            message = "解析错误";
        } else if (e instanceof ConnectException) {
            message = "连接失败";
        } else if (e instanceof ServerException) {
            message = ((ServerException) e).message;
            code = ((ServerException) e).code;
        } else {
            message = "未知错误";
        }
        if (!isShowProgress) {
            if (mLoadState != null) {
                mLoadState.postValue(StateConstants.REQUESTER_ERROR);
            }
        }else {
            ToastUtils.showToast(message);
        }
        onFailure(message, code);
    }

    @Override
    public void onNext(T t) {
        mLoadState.postValue(StateConstants.SERVER_SUCCESS);
        onSuccess(t);
    }

    /**
     * success
     *
     * @param t
     */
    public abstract void onSuccess(T t);

    /**
     * failure
     *
     * @param msg
     */
    public void onFailure(String msg, int code) {
    }

    ;
}
