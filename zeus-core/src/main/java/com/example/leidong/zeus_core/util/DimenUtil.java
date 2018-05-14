package com.example.leidong.zeus_core.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.example.leidong.zeus_core.app.Zeus;

/**
 * Created by Lei Dong on 2018/5/13.
 */
public class DimenUtil {
    /**
     * 得到屏幕的宽
     *
     * @return
     */
    public static final int getScreenWidth(){
        final Resources resources = Zeus.getApplication().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 得到屏幕的高
     *
     * @return
     */
    public static final int getScreenHeight(){
        final Resources resources = Zeus.getApplication().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }

}
