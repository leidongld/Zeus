package com.example.leidong.zeus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.example.leidong.zeus_core.delegates.ZeusDelegate;
import com.example.leidong.zeus_core.net.RestClient;
import com.example.leidong.zeus_core.net.callback.IError;
import com.example.leidong.zeus_core.net.callback.IFailure;
import com.example.leidong.zeus_core.net.callback.IRequest;
import com.example.leidong.zeus_core.net.callback.ISuccess;

public class ExampleDelegate extends ZeusDelegate{
    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        testRestClient();
    }

    private void testRestClient(){
        RestClient.builder()
                .url("http://news.baidu.com")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .request(new IRequest() {
                    @Override
                    public void onRequestStart() {

                    }

                    @Override
                    public void onRequestEnd() {

                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .build()
                .get();
    }
}
