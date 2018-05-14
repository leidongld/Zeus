package com.example.leidong.zeus_core.net.callback;

import android.os.Handler;

import com.example.leidong.zeus_core.ui.LoaderStyle;
import com.example.leidong.zeus_core.ui.ZeusLoader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lei Dong on 2018/5/6.
 */
public class RequestCallbacks implements Callback<String>{
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final LoaderStyle LOADER_STYLE;
    private static final Handler HANDLER = new Handler();


    public RequestCallbacks(IRequest request, ISuccess success, IFailure failure, IError error, LoaderStyle loaderStyle) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.LOADER_STYLE = loaderStyle;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if(response.isSuccessful()){
            if(call.isExecuted()){
                if(SUCCESS != null){
                    SUCCESS.onSuccess(response.body());
                }
            }
        }
        else{
            if(ERROR != null){
                ERROR.onError(response.code(), response.message());
            }
        }

        if(LOADER_STYLE != null){
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ZeusLoader.stopLoading();
                }
            }, 1000);
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if(FAILURE != null){
            FAILURE.onFailure();
        }

        if(REQUEST != null){
            REQUEST.onRequestEnd();
        }
    }
}
