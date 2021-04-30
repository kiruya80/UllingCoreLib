package com.ulling.lib.core.coroutine

import com.ulling.lib.core.common.QcDefine
import com.ulling.lib.core.utils.QcLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class QcUICoroutineScope(private val dispatchers: CoroutineContext = Dispatchers.Main) : QcBaseCoroutineScope {

    override val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = dispatchers + job

    override fun releaseCoroutine() {
        if (QcDefine.DEBUG_FLAG) {
            QcLog.d("onRelease coroutine")
        }
        job.cancel()
    }
}