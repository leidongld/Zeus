package com.example.leidong.zeus_core.ui;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.leidong.zeus_core.R;
import com.example.leidong.zeus_core.util.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by Lei Dong on 2018/5/6.
 */
public class ZeusLoader {

    //宽高比
    private static final int LOADER_SIZE_SCALE = 8;

    //偏移量
    private static final int LOADER_OFFSET_SCALE = 10;

    public static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();

    private static final String DEFAULT_LOADER = LoaderStyle.BallClipRotatePulseIndicator.name();

    public static void showLoading(Context context, Enum<LoaderStyle> type){
        showLoading(context, type.name());
    }

    /**
     * 显示加载
     *
     * @param context
     * @param type
     */
    public static void showLoading(Context context, String type){
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);

        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type, context);
        dialog.setContentView(avLoadingIndicatorView);

        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();

        final Window dialogWindow = dialog.getWindow();

        if(dialogWindow != null){
            final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth / LOADER_SIZE_SCALE;
            lp.height = deviceHeight / LOADER_SIZE_SCALE;

            lp.height = lp.height + deviceHeight / LOADER_OFFSET_SCALE;
            lp.gravity = Gravity.CENTER;
        }

        LOADERS.add(dialog);
        dialog.show();

    }

    /**
     * 显示Loading
     */
    public static void showLoading(Context context){
        showLoading(context, DEFAULT_LOADER);
    }

    /**
     * 停止Loading
     */
    public static void stopLoading(){
        for(AppCompatDialog dialog : LOADERS){
            if(dialog != null
                    && dialog.isShowing()) {
                dialog.cancel();
            }
        }
    }

}
