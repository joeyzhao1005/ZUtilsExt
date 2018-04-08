package com.kit.utils;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;

import com.kit.utils.log.Zog;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RxUtils {

    private RxUtils() {
    }


    public static <T> void computation(LifecycleProvider provider, final RxUtils.RxSimpleTask task, Object... objects) {

        Observable observable = Observable.create((e) -> {
            Zog.i("newThread subscribe");
            Object obj = task.doSth(objects);
            if (obj == null) {
                obj = new Object();
            }

            e.onNext(obj);
            e.onComplete();
        })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        if (provider != null) {
            if (provider instanceof RxAppCompatActivity) {
                observable.compose(provider.<T>bindUntilEvent(ActivityEvent.DESTROY));
            }else if (provider instanceof RxFragment) {
                observable.compose(provider.<T>bindUntilEvent(FragmentEvent.DESTROY));
            }
        }
        observable.subscribe(new DisposableObserver<T>() {
            public void onNext(T o) {
                if (!this.isDisposed()) {
                    Zog.i("newThread onNext");
                    task.onNext(o);
                }
            }

            public void onError(Throwable e) {
                if (!this.isDisposed()) {
                    Zog.i("newThread onError");
                    task.onError(e);
                }
            }

            public void onComplete() {
                if (!this.isDisposed()) {
                    Zog.i("newThread onComplete");
                    task.onComplete();
                }
            }
        });
    }

    public static <T> void newThread(LifecycleProvider provider, final RxUtils.RxSimpleTask task, Object... objects) {

        Observable observable = Observable.create((e) -> {
            Zog.i("newThread subscribe");
            Object obj = task.doSth(objects);
            if (obj == null) {
                obj = new Object();
            }

            e.onNext(obj);
            e.onComplete();
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        if (provider != null) {
            if (provider instanceof RxAppCompatActivity) {
                observable.compose(provider.<T>bindUntilEvent(ActivityEvent.DESTROY));
            }else if (provider instanceof RxFragment) {
                observable.compose(provider.<T>bindUntilEvent(FragmentEvent.DESTROY));
            }
        }
        observable.subscribe(new DisposableObserver<T>() {
            public void onNext(T o) {
                if (!this.isDisposed()) {
                    Zog.i("newThread onNext");
                    task.onNext(o);
                }
            }

            public void onError(Throwable e) {
                if (!this.isDisposed()) {
                    Zog.i("newThread onError");
                    task.onError(e);
                }
            }

            public void onComplete() {
                if (!this.isDisposed()) {
                    Zog.i("newThread onComplete");
                    task.onComplete();
                }
            }
        });
    }

    public static <T> void io(LifecycleProvider provider, final RxUtils.RxSimpleTask task) {

        Observable observable = Observable.create((e) -> {
            Object obj = task.doSth(new Object[0]);
            if (obj == null) {
                obj = new Object();
            }

            e.onNext(obj);
            e.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        if (provider != null) {
            if (provider instanceof RxAppCompatActivity) {
                observable.compose(provider.<T>bindUntilEvent(ActivityEvent.DESTROY));
            }else if (provider instanceof RxFragment) {
                observable.compose(provider.<T>bindUntilEvent(FragmentEvent.DESTROY));
            }
        }
        observable.subscribe(new DisposableObserver<T>() {
            public void onNext(T o) {
                if (!this.isDisposed()) {
                    Zog.i("onNext");
                    task.onNext(o);
                }
            }

            public void onError(Throwable e) {
                if (!this.isDisposed()) {
                    Zog.i("onError");
                    task.onError(e);
                }
            }

            public void onComplete() {
                if (!this.isDisposed()) {
                    Zog.i("onComplete");
                    task.onComplete();
                }
            }
        });
    }


    public abstract static class RxSimpleTask<T> {


        public T doSth(Object... objects) {
            return null;
        }

        public void onNext(T returnData) {
        }

        public void onError(Throwable e) {
            Zog.showException(e);
        }

        public void onComplete() {
        }
    }
}
