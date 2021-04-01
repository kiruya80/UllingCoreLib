package com.ulling.lib.core.listener

import android.annotation.SuppressLint
import android.view.View
import com.ulling.lib.core.base.QcBaseApplication
import com.ulling.lib.core.utils.QcLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.observers.BlockingBaseObserver
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

/**
 *
 *
 * Class:
 * Created by 19001662 on 2021-04-01 오후 4:04
 *
 * @version 1.0.0
 *
 * Description:
 *
 * RxJava를 이용한 클릭 이벤트 처리
 *
 *  throttleFirst
 *  ㄴ 최초 이벤트 이후 THRESHOLD_MILLIS동안 다른 이벤트 무시
 *
 *  throttleLast
 *  ㄴ 마지막 이벤트 이후
 *
 *
 *
 * https://answer-id.com/ko/56003895
 *
val singleClickListener = object : SingleRxClickListener(){
override fun onClicked(v: View) {
QcLog.e("onClick SingleRxClickListener ===================== ")
when (v.id) {
R.id.root ->{
QcLog.e("onClick root ===================== ")
val position:Int = v.getTag() as Int
}
}
}
}
 **/
abstract class OnSingleRxClickListener : View.OnClickListener {
    private val publishSubject: PublishSubject<View> = PublishSubject.create()
    private val THRESHOLD_MILLIS: Long = 500L

    abstract fun onClicked(v: View)

    override fun onClick(v: View?) {
        if (v != null) {
            publishSubject.onNext(v)
        }
    }

    init {
        QcLog.e("init =============== ")
        // todo 이렇게 사용하는 경우 뷰 id를 다 다르게 해야한다 중복불가 다른 방법이 있나???
//        publishSubject = QcBaseApplication.getInstance().getPublishSubject()

//        publishSubject.throttleFirst(THRESHOLD_MILLIS, TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe { v ->
//                    onClicked(v)
//                }

        publishSubject.throttleFirst(THRESHOLD_MILLIS, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BlockingBaseObserver<View?>() {
                    override fun onNext(v: View) {
                        QcLog.i("onNext ===================== ")
                        onClicked(v)
                    }

                    override fun onError(e: Throwable) {
                        QcLog.i("onError =========== $e")
                    }
                })
    }
}

//import android.view.View;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.annotations.NonNull;
//import io.reactivex.internal.observers.BlockingBaseObserver;
//import io.reactivex.subjects.PublishSubject;
//import java.util.concurrent.TimeUnit;
//
//public abstract class SingleRxClickListener implements View.OnClickListener {
//
//    private static final long THRESHOLD_MILLIS = 2000L;
//    private final PublishSubject<View> viewPublishSubject = PublishSubject.<View>create();
//
//    public SingleRxClickListener() {
//        viewPublishSubject.throttleFirst(THRESHOLD_MILLIS, TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BlockingBaseObserver<View>() {
//                    @Override
//                    public void onNext(@NonNull View v) {
//                        QcLog.e("onNext ===================== ");
//                        onClicked(v);
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        QcLog.e("onError =========== ");
//                    }
//                });
//    }
//
//    @Override
//    public void onClick(View v) {
//        viewPublishSubject.onNext(v);
//    }
//
//    public abstract void onClicked(View v);
//}