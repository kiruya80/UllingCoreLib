package com.ulling.lib.core.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

interface QcBaseCoroutineScope : CoroutineScope {

    val job: Job

    /**
     * Coroutine job cancel
     */
    fun releaseCoroutine()
}