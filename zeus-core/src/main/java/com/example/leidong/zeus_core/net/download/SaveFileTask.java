package com.example.leidong.zeus_core.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.leidong.zeus_core.app.Zeus;
import com.example.leidong.zeus_core.net.callback.IRequest;
import com.example.leidong.zeus_core.net.callback.ISuccess;
import com.example.leidong.zeus_core.util.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by Lei Dong on 2018/5/15.
 */
public class SaveFileTask extends AsyncTask<Object, Void, File>{
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    public SaveFileTask(IRequest request, ISuccess success) {
        REQUEST = request;
        SUCCESS = success;
    }


    @Override
    protected File doInBackground(Object... params) {
        String downloadDir = (String) params[0];
        String extention = (String) params[1];
        final ResponseBody body = (ResponseBody) params[2];
        final String name = (String) params[3];
        final InputStream inputStream = body.byteStream();
        if(downloadDir == null || downloadDir.equals("")){
            downloadDir = "down_loads";
        }
        if(extention == null || extention.equals("")){
            extention = "";
        }
        if(name == null || name.equals("")){
            return FileUtil.writeToDisk(inputStream, downloadDir, extention.toUpperCase(), extention);
        }
        else{
            return FileUtil.writeToDisk(inputStream, downloadDir, name);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if(SUCCESS != null){
            SUCCESS.onSuccess(file.getPath());
        }
        if(REQUEST != null){
            REQUEST.onRequestEnd();
        }
    }

    private void autoInstallApk(File file){
        if(FileUtil.getExtension(file.getPath()).equals("apk")){
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            Zeus.getApplication().startActivity(install);
        }
    }
}
