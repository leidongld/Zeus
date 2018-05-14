package com.example.leidong.zeus_core.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;

import com.example.leidong.zeus_core.R;
import com.example.leidong.zeus_core.delegates.ZeusDelegate;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * @author Lei Dong
 */
public abstract class ProxyActivity extends SupportActivity {
    public abstract ZeusDelegate setRootDelegate();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }

    private void initContainer(@Nullable Bundle savedInstanceState){
        final ContentFrameLayout container = new ContentFrameLayout(this);
        container.setId(R.id.delegate_container);
        setContentView(container);
        if(savedInstanceState == null){
            loadRootFragment(R.id.delegate_container, setRootDelegate());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}
