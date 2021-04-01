package com.ulling.lib.core.listener

import android.annotation.SuppressLint
import android.view.View
import com.ulling.lib.core.utils.QcLog
import io.reactivex.android.schedulers.AndroidSchedulers
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
@SuppressLint("CheckResult")
abstract class OnSingleRxClickListener : View.OnClickListener {
    private val publishSubject: PublishSubject<View> = PublishSubject.create()
    private val THRESHOLD_MILLIS: Long = 600L

    abstract fun onClicked(v: View)

    override fun onClick(p0: View?) {
        if (p0 != null) {
            QcLog.e("Clicked occurred")
            publishSubject.onNext(p0)
        }
    }

    init {
        publishSubject.throttleFirst(THRESHOLD_MILLIS, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { v ->
                    onClicked(v)
                }
    }
}

//import android.view.View;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.annotations.NonNull;
//import io.reactivex.internal.observers.BlockingBaseObserver;
//import io.reactivex.subjects.PublishSubject;
//import java.util.concurrent.TimeUnit;
//
//public abstract  class SingleRxClickListener implements View.OnClickListener {
//    private static final long THRESHOLD_MILLIS = 2000L;
//    private final PublishSubject<View> viewPublishSubject = PublishSubject.<View>create();
//
//    public SingleRxClickListener() {
//        viewPublishSubject.throttleFirst(THRESHOLD_MILLIS, TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
////            .subscribe(new Action1<View>() {
////                @Override
////                public void call(View view) {
////                    onClicked(view);
////                }
////            }
//                .subscribe(new BlockingBaseObserver<View>() {
//                    @Override
//                    public void onNext(@NonNull View view) {
//                        QcLog.e("onNext ===================== ");
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        QcLog.e("onError =========== ");
//                    }
//                } );
//    }
//
//    @Override
//    public void onClick(View v) {
//        QcLog.e("onClick ===================== ");
//        viewPublishSubject.onNext(v);
//    }
//
//    public abstract void onClicked(View v);
//}