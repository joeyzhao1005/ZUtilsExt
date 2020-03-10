package com.kit.ui.base;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.kit.utils.DeviceUtils;
import com.kit.utils.intent.ArgumentsManager;
import com.kit.utils.log.Zog;

import java.lang.ref.WeakReference;

/**
 * @author Zhao
 * <p>
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BaseV4Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the newInstance() factory method to
 * create an instance of this fragment.
 */
public abstract class BaseV4Fragment extends LifecycleKotlinCoroutineFragment implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener {

    public SwitchCompat getSwitchCompat(@IdRes int viewId) {
        return getView(viewId);
    }

    public Spinner getSpinner(@IdRes int viewId) {
        return getView(viewId);
    }

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


    @Nullable
    public <T extends Fragment> T getFragment(int id) {
        Context context = getContext();
        if (context instanceof AppCompatActivity) {
            return (T) ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(id);
        }

        return null;
    }


    @Nullable
    public ViewGroup getRootLayout() {
        if (layout instanceof ViewGroup) {
            return (ViewGroup) layout;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(@IdRes int viewId) {
        return getView(layout, viewId);
    }


    @SuppressWarnings("unchecked")
    public <T extends View> T getView(View layout, @IdRes int viewId) {
        if (views == null) {
            Zog.e("views is null");
            return null;
        }
        View view = null;
        WeakReference<View> weakReference = views.get(viewId);
        if (weakReference != null) {
            view = weakReference.get();
        }
        if (view == null) {
            if (layout == null) {
                return null;
            }
            try {
                view = layout.findViewById(viewId);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            if (view != null) {
                views.put(viewId, new WeakReference(view));
            } else {
                return null;
            }
        }
        return (T) view;
    }

    private SparseArray<WeakReference<View>> views;

    public boolean isAlive() {
        return !isDead() && isAdded();
    }

    public boolean isDead() {
        return isRemoving() || isDetached();
    }

    public boolean isShowing() {
        return isShowing;
    }

    public boolean isCreated() {
        return layout != null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        views = new SparseArray<>();
        super.onCreate(savedInstanceState);

        getExtras();
    }

    @Override
    public void onResume() {
        super.onResume();
        isShowing = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        isShowing = false;
    }

    @Override
    public void onDestroy() {
        destroy();
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return createView(inflater, container,
                savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWidget(layout);
        initWidgetWithExtra();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        if (layout != null) {
            layout.setOnTouchListener(null);
        }

        mListener = null;
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    protected void getExtras() {
    }

    protected abstract int layoutResId();

    public void initWidget(@NonNull View layout) {
    }

    protected boolean isTouchPenetrable() {
        return true;
    }

    public int getLogicScreenWidth(){
        return getScreenWidth();
    }
    public int getScreenWidth(){
        return DeviceUtils.getScreenWidth(getContext());
    }


    /**
     * 初始化界面
     */
    protected View createView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        if (layout != null) {
            ViewGroup parent = (ViewGroup) layout.getParent();
            if (parent != null) {
                parent.removeView(layout);
            }
            return layout;
        }
        layout = inflater.inflate(layoutResId(), container, false);

        if (!isTouchPenetrable()) {
            layout.setOnTouchListener(this);
            layout.setClickable(true);
            layout.findFocus();
        }
        return layout;
    }

    public final <T extends View> T findViewById(int id) {
        return layout.findViewById(id);
    }

    @Override
    public void onClick(View view) {
        if (view == null || view.getId() == 0) {
            return;
        }

        onViewClick(view);
    }

    public void onViewClick(@NonNull View view) {
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


    @Override
    public boolean onLongClick(View view) {
        if (view == null || view.getId() == 0) {
            return false;
        }

        onViewLongClick(view);

        return true;
    }

    public void onViewLongClick(@NonNull View view) {
    }

    protected void setOnLongClickListener(View... views) {
        if (views == null || views.length <= 0) {
            return;
        }

        for (View v : views) {
            if (v == null) {
                continue;
            }
            v.setOnLongClickListener(this);
        }
    }


    /**
     * 去网络或者本地加载数据
     */
    public void initWidgetWithExtra() {
    }


    public void destroy() {
        ArgumentsManager.get().destory(this);
        views.clear();

//        onPause();
//        onStop();
//        onDestroyView();
//        onDestroy();
//        onDetach();
    }

    public OnFragmentInteractionListener mListener;


    public BaseV4Fragment() {
        // Required empty public constructor
    }


    protected int navigationBarHeight;
    protected View layout;

    protected boolean isShowing;
}
