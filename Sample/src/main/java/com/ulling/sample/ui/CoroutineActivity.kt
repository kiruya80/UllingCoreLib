package com.ulling.sample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MyActivity : AppCompatActivity(), CoroutineScope {

    // Job을 등록할 수 있도록 초기화
    lateinit var job: Job

    // 기본 Main Thread 정의와 job을 함께 초기화
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    // 작업 중이던 모든 job을 종 children을 종료 처리
    override fun onDestroy() {
        super.onDestroy()
        job.cancel() // Cancel job on activity destroy. After destroy all children jobs will be cancelled automatically
    }
    /*
    15,480
    16,740 0.8268
    11,160
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
