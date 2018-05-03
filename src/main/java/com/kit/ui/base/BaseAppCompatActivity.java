package com.kit.ui.base;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kit.app.ActivityManager;
import com.kit.app.UIHandler;
import com.kit.utils.intent.IntentManager;
import com.kit.utils.log.Zog;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseAppCompatActivity extends RxAppCompatActivity implements BaseV4Fragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        setContentView(layoutResID());
        this.views = new SparseArray<>();
        getExtra();
        initWidget();
        loadData();
        initWidgetWithExtra();

        UIHandler.prepare();

        ActivityManager.getInstance().pushActivity(this);
    }

    public boolean isShowing() {
        return !isFinishing();
    }

    /**
     * 去网络或者本地加载数据
     */
    protected boolean loadData() {
        return true;
    }


    protected void initWidgetWithExtra() {
    }

    /**
     * i
     * 获得上一个Activity传过来的值
     */
    protected boolean getExtra() {

        return true;
    }

    /**
     * 初始化界面
     */
    protected void initWindow() {
    }

    /**
     * 初始化界面
     */
    protected void initWidget() {
    }

    protected abstract int layoutResID();


    public TextView getTextView(@IdRes int viewId) {
        return getView(viewId);
    }

    public ImageView getImageView(@IdRes int viewId) {
        return getView(viewId);
    }

    public ImageButton getImageButton(@IdRes int viewId) {
        return getView(viewId);
    }

    public Button getButton(@IdRes int viewId) {
        return getView(viewId);
    }

    public EditText getEditText(@IdRes int viewId) {
        return getView(viewId);
    }

    public RatingBar getRatingBar(@IdRes int viewId) {
        return getView(viewId);
    }

    public ProgressBar getProgressBar(@IdRes int viewId) {
        return getView(viewId);
    }

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
            destory();
        }
    }


    @Override
    protected void onDestroy() {
        try {
            super.onDestroy();
        } catch (RuntimeException e) {
            Zog.showException(e);
        }
    }


    public void destory() {
        views.clear();
        ActivityManager.getInstance().popActivity(this);
        IntentManager.get().destory(this);
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

}
