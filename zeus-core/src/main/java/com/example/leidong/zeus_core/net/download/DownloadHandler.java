package com.example.leidong.zeus_core.net.download;

import android.os.AsyncTask;

import com.example.leidong.zeus_core.net.RestCreater;
import com.example.leidong.zeus_core.net.callback.IError;
import com.example.leidong.zeus_core.net.callback.IFailure;
import com.example.leidong.zeus_core.net.callback.IRequest;
import com.example.leidong.zeus_core.net.callback.ISuccess;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lei Dong on 2018/5/15.
 */
public class DownloadHandler {
    private final String URL;
    private final Map<String, Object> PARAMS = RestCreater.getParams();

    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;

    public DownloadHandler(
            String url,
            IRequest request,
            String download_dir,
            String extension,
            String name,
            ISuccess success,
            IFailure failure,
            IError error) {
        URL = url;
        REQUEST = request;
        DOWNLOAD_DIR = download_dir;
        EXTENSION = extension;
        NAME = name;
        SUCCESS = success;
        FAILURE = failure;
        ERROR = error;
    }

    public final void handleDowmload(){
        if(REQUEST != null){
            REQUEST.onRequestStart();
        }
        RestCreater.getRestService().download(URL, PARAMS).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    final ResponseBody body = response.body();
                    final SaveFileTask task = new SaveFileTask(REQUEST, SUCCESS);
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DOWNLOAD_DIR, EXTENSION, response, NAME);

                    if (task.isCancelled()) {
                        if (REQUEST != null) {
                            REQUEST.onRequestEnd();
                        }
                    }
                }
                else{
                    if(ERROR != null){
                        ERROR.onError(response.code(), response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(FAILURE != null){
                    FAILURE.onFailure();
                }
            }
        });
    }
}
