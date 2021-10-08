package com.kit.ui.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.kit.app.ActivityManager;
import com.kit.utils.log.Zog;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author joeyzhao
 */
public abstract class BaseAppCompatActivity<VB extends ViewBinding> extends LifecycleKotlinCoroutineActivity implements BaseFragment.OnFragmentInteractionListener, View.OnClickListener {


    private boolean isShowing;
    protected VB bindView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        darkMode();
        viewBinding();
        this.views = new SparseArray<>();
        getExtras();
        initWidget();
        loadData();
        initWidgetWithExtra();


        ActivityManager.getInstance().pushActivity(this);
        isShowing = true;
    }


    @SuppressWarnings("unchecked")
    @Nullable
    private Class<VB> findParameterizedType(@Nullable Class clazz) {
        if (clazz == null) {
            return null;
        }
        Type superclass = clazz.getGenericSuperclass();
        Class<VB> aClass = null;
        if (superclass != null) {

            if (superclass instanceof ParameterizedType) {
                Type[] types = ((ParameterizedType) superclass).getActualTypeArguments();
                try {
                    aClass = (Class<VB>) types[0];
                } catch (Exception ignore) {
                }
            } else {
                aClass = findParameterizedType(clazz.getSuperclass());
            }

        }
        return aClass;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    protected VB viewBinding() {
        Class<VB> aClass = findParameterizedType(getClass());
        if (aClass != null) {
            try {
                Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class);
                bindView = (VB) method.invoke(null, getLayoutInflater());
                if (bindView != null) {
                    setContentView(bindView.getRoot());
                }
            } catch (Exception e) {
                e.printStackTrace();
                setContentView(layoutResId());
            }
        } else {
            setContentView(layoutResId());
        }

        return bindView;
    }

    protected void darkMode() {
//        if (DarkMode.isDarkMode()) {
//            setTheme(R.style.main_theme_dark);
//        }else {
//            setTheme(R.style.main_theme_light);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isShowing = true;
    }

    public boolean isShowing() {
        return isShowing;
    }

    /**
     * 去网络或者本地加载数据
     */
    protected void loadData() {
    }


    protected void initWidgetWithExtra() {
    }

    /**
     * i
     * 获得上一个Activity传过来的值
     */
    protected void getExtras() {
    }

    /**
     * 初始化界面
     */
    protected void initWindow() {
        //解决android 9.0水滴屏/刘海屏有黑边的问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(lp);
        }
    }

    /**
     * 初始化界面
     */
    protected void initWidget() {


    }

    @Deprecated
    protected int layoutResId() {
        return 0;
    }


    public boolean isTranslucentOrFloating() {
        return false;
    }


    @Deprecated
    public TextView getTextView(@IdRes int viewId) {
        return getView(viewId);
    }

    @Deprecated
    public ImageView getImageView(@IdRes int viewId) {
        return getView(viewId);
    }

    @Deprecated
    public ImageButton getImageButton(@IdRes int viewId) {
        return getView(viewId);
    }

    @Deprecated
    public Button getButton(@IdRes int viewId) {
        return getView(viewId);
    }

    @Deprecated
    public EditText getEditText(@IdRes int viewId) {
        return getView(viewId);
    }

    @Deprecated
    public RatingBar getRatingBar(@IdRes int viewId) {
        return getView(viewId);
    }


    @Deprecated
    public ProgressBar getProgressBar(@IdRes int viewId) {
        return getView(viewId);
    }

    protected void onViewClick(View view) {

    }

    @Override
    public void onClick(View view) {
        if (view == null || view.getId() == 0) {
            return;
        }

        onViewClick(view);

    }

    protected void setOnClickListener(View... views) {
        if (views == null || views.length <= 0) {
            return;
        }

        for (View v : views) {
            if (v == null) {
                continue;
            }
            v.setOnClickListener(this);
        }
    }

    @Deprecated
    public View view(@IdRes int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = findViewById(viewId);
            views.put(viewId, view);
        }
        return view;
    }


    @Deprecated
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(@IdRes int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    private SparseArray<View> views;


    @Override
    protected void onStop() {
        super.onStop();
        if (isFinishing()) {
            destroy();
        }
        isShowing = false;
    }


    @Override
    protected void onDestroy() {
        try {
            super.onDestroy();
        } catch (RuntimeException e) {
            Zog.showException(e);
        }
    }


    public void destroy() {
        ActivityManager.getInstance().popActivity(this);
        views.clear();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//
//
//        return super.onOptionsItemSelected(item);
//    }
//


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @SuppressWarnings("unchecked")
    @Nullable
    protected <T> T getExtra(String key, T defaultValue) {
        Intent intent = getIntent();
        if (intent == null) {
            return defaultValue;
        }
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return defaultValue;
        }

        Object o = bundle.get(key);
        if (o == null) {
            return defaultValue;
        }

        return (T) o;
    }
}
