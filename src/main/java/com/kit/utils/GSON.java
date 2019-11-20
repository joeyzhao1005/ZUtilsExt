package com.kit.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kit.app.Callback;
import com.kit.utils.log.Zog;
import com.trello.rxlifecycle3.LifecycleProvider;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.android.FragmentEvent;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle3.components.support.RxFragment;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * json解析类
 */
public class GSON {

    Gson gsonItem;
    String jsonStr;
    WeakReference<LifecycleProvider> lifecycleProviderWeakReference;
    boolean callbackInUI = false;

    public static GSON create() {
        return create(false);
    }

    public static GSON create(boolean lenient) {
        return create(null, lenient);
    }

    public static GSON create(LifecycleProvider lifecycleProvider) {
        return create(lifecycleProvider, false);
    }


    public static GSON create(LifecycleProvider lifecycleProvider, boolean lenient) {
        GsonBuilder builder = new GsonBuilder();
        if (lenient) {
            builder = builder.setLenient();
        }
        Gson gsonItem = builder.create();


        GSON gson = new GSON();
        gson.lifecycle(lifecycleProvider);
        gson.gsonItem = gsonItem;

        return gson;
    }

    //////////to json string////////
    public String json(Object o) {
        if (o == null) {
            return null;
        }
        return gsonItem.toJson(o);
    }


    public void json(Object o, Callback<String> callback) {
        if (o == null) {
            if (callback != null) {
                callback.call(null);
            }
        }

        Observable observable = Observable.create((e) -> {
            String t = null;
            try {
                t = gsonItem.toJson(o);
            } catch (Exception e2) {
                Zog.showException(e2);
            }

            e.onNext(t == null ? "" : t);
            e.onComplete();
        })
                .subscribeOn(Schedulers.computation())
                .observeOn(callbackInUI ? AndroidSchedulers.mainThread() : Schedulers.computation());
        if (lifecycleProviderWeakReference != null && lifecycleProviderWeakReference.get() != null) {
            if (lifecycleProviderWeakReference.get() instanceof RxAppCompatActivity) {
                observable.compose(((RxAppCompatActivity) lifecycleProviderWeakReference.get()).bindUntilEvent(ActivityEvent.DESTROY));
            } else if (lifecycleProviderWeakReference.get() instanceof RxFragment) {
                observable.compose(((RxFragment) lifecycleProviderWeakReference.get()).bindUntilEvent(FragmentEvent.DESTROY));
            }
        }

        DisposableObserver disposableObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(String str) {
                if (!this.isDisposed()) {
                    if (callback != null) {
                        callback.call(str);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                if (!this.isDisposed()) {
                    if (callback != null) {
                        callback.call(null);
                    }
                }
            }

            @Override
            public void onComplete() {
                if (!this.isDisposed()) {
                }
            }
        };

        observable.subscribe(disposableObserver);
    }


    //////////to json string////////


    public GSON data(String jsonStr) {
        this.jsonStr = jsonStr;
        return this;
    }

    public GSON lifecycle(LifecycleProvider lifecycleProvider) {
        lifecycleProviderWeakReference = new WeakReference<LifecycleProvider>(lifecycleProvider);
        return this;
    }

    /**
     * 仅异步调用有用
     *
     * @return
     */
    public GSON callbackInUI() {
        this.callbackInUI = true;
        return this;
    }

    /**
     * 异步获取返回结果
     *
     * @param callback
     */
    public <T> void get(Class<T> clazz, Callback<T> callback) {
        if (jsonStr == null || clazz == null) {
            if (callback != null) {
                callback.call(null);
            }
        }

        Observable observable = Observable.create((e) -> {
            T t = null;
            try {
                t = gsonItem.fromJson(jsonStr, clazz);
            } catch (Exception e2) {
                Zog.showException(e2);
            }

            e.onNext(t == null ? "" : t);
            e.onComplete();
        })
                .subscribeOn(Schedulers.computation())
                .observeOn(callbackInUI ? AndroidSchedulers.mainThread() : Schedulers.computation());
        if (lifecycleProviderWeakReference != null && lifecycleProviderWeakReference.get() != null) {
            if (lifecycleProviderWeakReference.get() instanceof RxAppCompatActivity) {
                observable.compose(((RxAppCompatActivity) lifecycleProviderWeakReference.get()).bindUntilEvent(ActivityEvent.DESTROY));
            } else if (lifecycleProviderWeakReference.get() instanceof RxFragment) {
                observable.compose(((RxFragment) lifecycleProviderWeakReference.get()).bindUntilEvent(FragmentEvent.DESTROY));
            }
        }

        DisposableObserver disposableObserver = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object o) {
                if (!this.isDisposed()) {
                    if (callback != null) {
                        callback.call(o instanceof String ? null : (T) o);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                if (!this.isDisposed()) {
                    if (callback != null) {
                        callback.call(null);
                    }
                }
            }

            @Override
            public void onComplete() {
                if (!this.isDisposed()) {
                }
            }
        };

        observable.subscribe(disposableObserver);

    }


    /**
     * 异步获取返回结果
     *
     * @param callback
     */
    public <T> void get(Type typeOfT, Callback<T> callback) {
        if (jsonStr == null || typeOfT == null) {
            if (callback != null) {
                callback.call(null);
            }
        }

        Observable observable = Observable.create((e) -> {
            T t = null;
            try {
                t = gsonItem.fromJson(jsonStr, typeOfT);
            } catch (Exception e2) {
                Zog.showException(e2);
            }

            e.onNext(t == null ? "" : t);
            e.onComplete();
        })
                .subscribeOn(Schedulers.computation())
                .observeOn(callbackInUI ? AndroidSchedulers.mainThread() : Schedulers.computation());
        if (lifecycleProviderWeakReference != null && lifecycleProviderWeakReference.get() != null) {
            if (lifecycleProviderWeakReference.get() instanceof RxAppCompatActivity) {
                observable.compose(((RxAppCompatActivity) lifecycleProviderWeakReference.get()).bindUntilEvent(ActivityEvent.DESTROY));
            } else if (lifecycleProviderWeakReference.get() instanceof RxFragment) {
                observable.compose(((RxFragment) lifecycleProviderWeakReference.get()).bindUntilEvent(FragmentEvent.DESTROY));
            }
        }

        DisposableObserver disposableObserver = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object o) {
                if (!this.isDisposed()) {
                    if (callback != null) {
                        callback.call(o instanceof String ? null : (T) o);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                if (!this.isDisposed()) {
                    if (callback != null) {
                        callback.call(null);
                    }
                }
            }

            @Override
            public void onComplete() {
                if (!this.isDisposed()) {
                }
            }
        };

        observable.subscribe(disposableObserver);

    }


    /**
     * 同步获取json对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getSync(Class<T> clazz) {
        if (jsonStr == null || clazz == null) {
            return null;
        }
        return gsonItem.fromJson(jsonStr, clazz);
    }

    /**
     * 同步获取json列表
     *
     * @param <T>
     * @return
     */
    public <T> T getSync(Type typeOfT) {
        if (jsonStr == null || typeOfT == null) {
            return null;
        }
        return gsonItem.fromJson(jsonStr, typeOfT);
    }
}
