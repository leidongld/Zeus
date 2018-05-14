package com.example.leidong.zeus_core.net;

import android.content.Context;

import com.example.leidong.zeus_core.net.callback.IError;
import com.example.leidong.zeus_core.net.callback.IFailure;
import com.example.leidong.zeus_core.net.callback.IRequest;
import com.example.leidong.zeus_core.net.callback.ISuccess;
import com.example.leidong.zeus_core.net.callback.RequestCallbacks;
import com.example.leidong.zeus_core.ui.LoaderStyle;
import com.example.leidong.zeus_core.ui.ZeusLoader;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * @author Lei Dong
 */
public class RestClient {
    private final String URL;
    private final Map<String, Object> PARAMS = RestCreater.getParams();

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final File FILE;
    private final Context CONTEXT;


    public RestClient(
            String url,
            Map<String, Object> params,
            IRequest request,
            ISuccess success,
            IFailure failure,
            IError error,
            RequestBody body,
            Context context,
            LoaderStyle loaderStyle,
            File file) {
        this.URL = url;
        this.PARAMS.putAll(params);
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
        this.FILE = file;
    }

    public static RestClientBuilder builder(){
        return new RestClientBuilder();
    }

    public void request(HttpMethods method){
        final RestService restService = RestCreater.getRestService();
        Call<String> call = null;

        if(REQUEST != null){
            REQUEST.onRequestStart();
        }

        if(LOADER_STYLE != null){
            ZeusLoader.showLoading(CONTEXT, LOADER_STYLE);
        }

        switch(method){
            case GET:
                call = restService.get(URL, PARAMS);
                break;
            case POST:
                call = restService.post(URL, PARAMS);
                break;
            case POST_RAM:
                call = restService.postRaw(URL, BODY);
                break;
            case PUT:
                call = restService.put(URL, PARAMS);
                break;
            case PUT_RAM:
                call = restService.putRaw(URL, BODY);
                break;
            case DELETE:
                call = restService.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                call = RestCreater.getRestService().upload(URL, body);
                break;
            default:
                break;
        }

        if(call != null){
            call.enqueue(getRequestCallback());
        }
    }

    private Callback<String> getRequestCallback(){
        return new RequestCallbacks(REQUEST, SUCCESS, FAILURE, ERROR, LOADER_STYLE);
    }

    public final void get(){
        request(HttpMethods.GET);
    }

    public final void post(){
        if(BODY == null) {
            request(HttpMethods.POST);
        }
        else{
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("Params must be null.");
            }
            request(HttpMethods.POST_RAM);
        }
    }

    public final void put(){
        if(BODY == null) {
            request(HttpMethods.PUT);
        }
        else{
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("Params must be null.");
            }
            request(HttpMethods.PUT_RAM);
        }
    }

    public final void delete(){
        request(HttpMethods.DELETE);
    }
}
