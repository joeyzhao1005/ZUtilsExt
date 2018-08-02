package com.kit.utils;


import com.kit.utils.log.Zog;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;


import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RxUtils {


    public static <T> void computation(LifecycleProvider provider, final RxUtils.RxSimpleTask task, Object... objects) {
        computation(provider, 0, task, objects);
    }

    public static <T> void computation(LifecycleProvider provider, long delayMilliseconds, final RxUtils.RxSimpleTask task, Object... objects) {
        WeakReference lifecycleProviderWeakReference = new WeakReference<LifecycleProvider>(provider);

        if (task == null) {
            return;
        }
        Observable observable = Observable.create((e) -> {
//            Zog.i("newThread subscribe");
            Object obj = task.doSth(objects);
            if (obj == null) {
                obj = task.getDefault();
            }

            e.onNext(obj);
            e.onComplete();
        })
                .delay(delayMilliseconds, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        if (lifecycleProviderWeakReference.get() != null) {
            if (lifecycleProviderWeakReference.get() instanceof RxAppCompatActivity) {
                observable.compose(((RxAppCompatActivity) lifecycleProviderWeakReference.get()).bindUntilEvent(ActivityEvent.DESTROY));
            } else if (lifecycleProviderWeakReference.get() instanceof RxFragment) {
                observable.compose(((RxFragment) lifecycleProviderWeakReference.get()).bindUntilEvent(FragmentEvent.DESTROY));
            }
        }
        observable.subscribe(new DisposableObserver<T>() {
            @Override
            public void onNext(@NonNull T o) {
                if (!this.isDisposed()) {
//                    Zog.i("newThread onNext");
                    task.onNext(o);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (!this.isDisposed()) {
//                    Zog.i("newThread onError");
                    task.onError(e);
                }
            }

            @Override
            public void onComplete() {
                if (!this.isDisposed()) {
//                    Zog.i("newThread onComplete");
                    task.onComplete();
                }
            }
        });
    }

    public static <T> void newThread(LifecycleProvider provider, final RxUtils.RxSimpleTask task, Object... objects) {
        newThread(provider, 0, task, objects);
    }

    public static <T> void newThread(LifecycleProvider provider, long delayMilliseconds, final RxUtils.RxSimpleTask task, Object... objects) {
        WeakReference lifecycleProviderWeakReference = new WeakReference<LifecycleProvider>(provider);

        Observable observable = Observable.create((e) -> {
//            Zog.i("newThread subscribe");
            Object obj = task.doSth(objects);
            if (obj == null) {
                obj = task.getDefault();
            }

            e.onNext(obj);
            e.onComplete();
        })
                .delay(delayMilliseconds, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        if (lifecycleProviderWeakReference.get() != null) {
            if (lifecycleProviderWeakReference.get() instanceof RxAppCompatActivity) {
                observable.compose(((RxAppCompatActivity) lifecycleProviderWeakReference.get()).bindUntilEvent(ActivityEvent.DESTROY));
            } else if (lifecycleProviderWeakReference.get() instanceof RxFragment) {
                observable.compose(((RxFragment) lifecycleProviderWeakReference.get()).bindUntilEvent(FragmentEvent.DESTROY));
            }
        }
        observable.subscribe(new DisposableObserver<T>() {
            @Override
            public void onNext(@NonNull T o) {
                if (!this.isDisposed()) {
//                    Zog.i("newThread onNext");
                    task.onNext(o);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (!this.isDisposed()) {
//                    Zog.i("newThread onError");
                    task.onError(e);
                }
            }

            @Override
            public void onComplete() {
                if (!this.isDisposed()) {
//                    Zog.i("newThread onComplete");
                    task.onComplete();
                }
            }
        });
    }

    public static <T> void io(LifecycleProvider provider, final RxUtils.RxSimpleTask task) {
        io(provider, 0, task);
    }

    public static <T> void io(LifecycleProvider provider, long delayMilliseconds, final RxUtils.RxSimpleTask task) {
        WeakReference lifecycleProviderWeakReference = new WeakReference<LifecycleProvider>(provider);

        Observable observable = Observable.create((e) -> {
            Object obj = task.doSth(new Object[0]);
            if (obj == null) {
                obj = task.getDefault();
            }

            if(obj==null){
                Zog.e("task:"+task+" default is null");
            }
            e.onNext(obj);
            e.onComplete();
        })
                .delay(delayMilliseconds, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        if (lifecycleProviderWeakReference.get() != null) {
            if (lifecycleProviderWeakReference.get() instanceof RxAppCompatActivity) {
                observable.compose(((RxAppCompatActivity) lifecycleProviderWeakReference.get()).bindUntilEvent(ActivityEvent.DESTROY));
            } else if (lifecycleProviderWeakReference.get() instanceof RxFragment) {
                observable.compose(((RxFragment) lifecycleProviderWeakReference.get()).bindUntilEvent(FragmentEvent.DESTROY));
            }
        }
        observable.subscribe(new DisposableObserver<T>() {
            @Override
            public void onNext(@NonNull T o) {
                if (!this.isDisposed()) {
//                    Zog.i("onNext");
                    task.onNext(o);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (!this.isDisposed()) {
//                    Zog.i("onError");
                    task.onError(e);
                }
            }

            @Override
            public void onComplete() {
                if (!this.isDisposed()) {
//                    Zog.i("onComplete");
                    task.onComplete();
                }
            }
        });
    }

    private RxUtils() {
    }


    public abstract static class RxSimpleTask<T> {

        public T getDefault() {
            return null;
        }

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