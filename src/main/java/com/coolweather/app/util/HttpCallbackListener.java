package com.coolweather.app.util;

/**
 * Created by ZZH on 2016/08/25.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
