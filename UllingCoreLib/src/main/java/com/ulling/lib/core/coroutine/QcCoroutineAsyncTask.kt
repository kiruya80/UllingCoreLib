package com.ulling.lib.core.coroutine

import com.ulling.lib.core.utils.QcLog
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


abstract class QcCoroutineAsyncTask<Params, Result> {
    lateinit var scope: CoroutineScope

    // Job을 등록할 수 있도록 초기화
    // CoroutineScope의 동작을 제어할 객체
// 안드로이드 상에서는 Lifecycle을 활용할 수 있도록 도와준다.
    lateinit var job: Job

    // onPreExecute
    protected abstract fun onPreExecute()

    // doInBackground
    protected abstract fun doInBackground(params: Params?): Result?

    // onPostExecute
    protected abstract fun onPostExecute(result: Result?)

    fun cancel() {
        job?.cancel()
    }

    fun execute() {
        execute(null)
    }

    fun execute(params: Params?) {
        runBlocking {
            onPreExecute()
        }

        var result: Result? = null

        job = Job()
        scope = CoroutineScope(job + Dispatchers.Default)

        scope.launch {

            try {
                result = doInBackground(params)
            } catch (ex: Exception) {
                QcLog.e("CoroutineAsyncTask Exception : $ex")

                withContext(job + Dispatchers.Main) {
                    onPostExecute(result)
                }
                return@launch
            }

            withContext(job + Dispatchers.Main) {
                onPostExecute(result)
            }
        }
    }

    /**
     * 이하 샘플 테스트용
     */
    // coroutine의 스레드를 어떠한 형태로 사용할지 지정할 수 있다.
    val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    fun test1() {
        QcLog.e("test1 ====================================================================== ")
        job = Job()
        /**
         * runBlocking 내부 처리가 끝나면 다음으로 내려간다
         *
         * runBlocking에서는 자식 스레드가 완료될 때 까지 현재 스레드를 block 한다.
         */
        runBlocking {
            QcLog.e("STEP 1 delay ============= ") // 15466-15466
            delay(2000L)
            QcLog.e("STEP 1 ============= ") // 15466-15466
        }

        QcLog.e("STEP 2 ============= ")

        CoroutineScope(coroutineContext).launch {
            QcLog.e("STEP 4 delay ============= ") // 15466-15553
            delay(2000L)
            QcLog.e("STEP 4 ============= ") // 15466-15554
        }

        QcLog.e("STEP 3 ============= ")
    }

    fun test11() {
        QcLog.e("test11 ====================================================================== ")
        job = Job()

        CoroutineScope(coroutineContext).launch {
            QcLog.e("STEP 2 delay ============= ") // 15466-15553
            delay(500L)
            QcLog.e("STEP 2 ============= ") //
        }

        QcLog.e("STEP 1 ============= ")

        runBlocking {
            QcLog.e("STEP 3 delay ============= ") // 15466-15466
            delay(500L)
            QcLog.e("STEP 3 ============= ") // 15466-15466
        }

        QcLog.e("STEP 4 ============= ")
    }

    fun test2() {
        QcLog.e("test2 ====================================== ")
        runBlocking<Unit> {
            launch {
                delay(500L)
                QcLog.e("World")
            }
            QcLog.e("Hello")
            delay(500L)
        }
        QcLog.e("End function")

        QcLog.e("End test2 ==========================")
    }


    fun test3() {
        QcLog.e("test3 ====================================== ")
        runBlocking {
            val job = GlobalScope.launch {
                delay(500L)
                QcLog.e("World!")
            }
            job.join()
            QcLog.e("Hello,")
        }

        QcLog.e("End test3 ==========================")
    }

    fun test4() {
        QcLog.e("  test4 ==========================")
        val job = Job()
        CoroutineScope(Dispatchers.Default + job).launch {

            CoroutineScope(Dispatchers.Default + job).launch { // 여기에 job을 함께 초기화 한다.
                QcLog.e("Job one scope start")
                for (index in 0..20) {
                    if (isActive) {
                        QcLog.e("Job one scope index $index")
                        delay(1)
                    } else {
                        break
                    }
                }
                QcLog.e("Job one scope for end")
            }

            val jobTwo = launch {
                QcLog.e("Job two scope for start")
                for (index in 0..10) {
                    if (isActive) {
                        QcLog.e("Job two scope index $index")
                        delay(1)
                    } else {
                        break
                    }
                }
                QcLog.e("Job two scope for end")
            }
            jobTwo.join()
        }
        QcLog.e("  test4 END ==========================")
    }
}