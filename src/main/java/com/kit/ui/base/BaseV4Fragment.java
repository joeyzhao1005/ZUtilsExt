package com.kit.ui.base;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kit.utils.intent.ArgumentsManager;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * @author Zhao
 * <p>
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BaseV4Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the newInstance() factory method to
 * create an instance of this fragment.
 */
public abstract class BaseV4Fragment extends RxFragment implements View.OnClickListener, View.OnLongClickListener {


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
        if (views == null) {
            return null;
        }
        View view = views.get(viewId);
        if (view == null) {
            view = layout.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    private SparseArray<View> views;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        views = new SparseArray<>();
        super.onCreate(savedInstanceState);

        getExtra();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        destory();
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
        initWidgetWithExtra();
        loadData();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        super.onDetach();
        mListener = null;
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

    protected void getExtra() {
    }


    /**
     * 去网络或者本地加载数据
     */
    protected void loadData() {
    }

    protected abstract int layoutResID();

    public void initWidget(@NonNull View layout) {
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
        layout = inflater.inflate(layoutResID(), container, false);
        initWidget(layout);
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


    public void destory() {
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


    View layout;
}
