package com.kit.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.util.AndroidRuntimeException;

import com.kit.app.resouce.DrawableId;
import com.kit.utils.ActionBarUtils;
import com.kit.utils.ResWrapper;
import com.kit.utils.ResourceUtils;
import com.kit.utils.log.Zog;

import kotlinx.coroutines.CoroutineScope;

/**
 * @author joeyzhao
 */
public abstract class BaseActivity extends BaseAppCompatActivity implements BaseV4Fragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTheme();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void initTheme() {
        ActionBarUtils.setHomeActionBar(this, ResourceUtils.getDrawableId(this, DrawableId.ic_back));
    }

    @Override
    public void startActivity(Intent intent) {
        try {
            super.startActivity(intent);
        } catch (AndroidRuntimeException e) {
            Zog.showException(e);
        }
    }
}

