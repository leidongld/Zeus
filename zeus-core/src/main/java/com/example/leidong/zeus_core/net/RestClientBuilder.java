package com.example.leidong.zeus_core.net;

import android.content.Context;

import com.example.leidong.zeus_core.net.callback.IError;
import com.example.leidong.zeus_core.net.callback.IFailure;
import com.example.leidong.zeus_core.net.callback.IRequest;
import com.example.leidong.zeus_core.net.callback.ISuccess;
import com.example.leidong.zeus_core.ui.LoaderStyle;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Lei Dong on 2018/5/5.
 */
public class RestClientBuilder {
    private  String mUrl = null;
    private static final Map<String, Object> PARAMS = RestCreater.getParams();
    private IRequest mIRequest = null;
    private ISuccess mISuccess = null;
    private IFailure mIFailure = null;
    private IError mIError = null;
    private RequestBody mRequestBody = null;
    private Context mContext = null;
    private LoaderStyle mLoaderStyle = null;
    private File mFile = null;

    RestClientBuilder(){

    }

    public final RestClientBuilder url(String url){
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(Map<String, Object> params){
        //this.mParams = params;
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key, Object value){
        PARAMS.put(key, value);
        return this;
    }

    public final RestClientBuilder file(File file){
        this.mFile = file;
        return this;
    }

    public final RestClientBuilder file(String file){
        this.mFile = new File(file);
        return this;
    }

    public final RestClientBuilder raw(String raw){
        this.mRequestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuilder success(ISuccess iSuccess){
        this.mISuccess = iSuccess;
        return this;
    }

    public final RestClientBuilder error(IError iError){
        this.mIError = iError;
        return this;
    }

    public final RestClientBuilder failure(IFailure iFailure){
        this.mIFailure = iFailure;
        return this;
    }

    public final RestClientBuilder request(IRequest iRequest){
        this.mIRequest = iRequest;
        return this;
    }

    private Map<String, Object> checkParams(){
        return PARAMS;
    }

    public final RestClientBuilder loader(Context context, LoaderStyle loaderStyle){
        this.mContext = context;
        this.mLoaderStyle = loaderStyle;
        return this;
    }

    public final RestClientBuilder loader(Context context){
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallSpinFadeLoaderIndicator;
        return this;
    }

    public final RestClient build(){
        return new RestClient(mUrl, PARAMS, mIRequest, mISuccess, mIFailure, mIError, mRequestBody, mContext, mLoaderStyle, mFile);
    }
}
