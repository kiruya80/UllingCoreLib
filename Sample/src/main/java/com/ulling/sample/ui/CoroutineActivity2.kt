package com.ulling.sample.ui

import android.os.Bundle
import com.ulling.lib.core.coroutine.QcCoroutineScopeActivity
import kotlinx.coroutines.*

class MyActivity2 : QcCoroutineScopeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // 작업 중이던 모든 job을 종 children을 종료 처리
    override fun onDestroy() {
        super.onDestroy()
        releaseCoroutine()
    }

    /*
     * Note how coroutine builders are scoped: if activity is destroyed or any of the launched coroutines
     * in this method throws an exception, then all nested coroutines are cancelled.
     */
    fun loadDataFromUI() = launch { // <- extension on current activity, launched in the main thread
        val ioData = async(Dispatchers.IO) { // <- extension on launch scope, launched in IO dispatcher
            // blocking I/O operation
        }
        // do something else concurrently with I/O
        val data = ioData.await() // wait for result of I/O
//        draw(data) // can draw in the main thread
    }
}
