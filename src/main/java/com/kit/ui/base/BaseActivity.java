package com.kit.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.util.AndroidRuntimeException;

import androidx.viewbinding.ViewBinding;

import com.kit.app.resouce.DrawableId;
import com.kit.utils.ActionBarUtils;
import com.kit.utils.ResourceUtils;
import com.kit.utils.log.Zog;

/**
 * @author joeyzhao
 */
public abstract class BaseActivity<VB extends ViewBinding> extends BaseAppCompatActivity<VB> implements BaseFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
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

