package com.ulling.lib.core.coroutine

import androidx.appcompat.app.AppCompatActivity

abstract class QcCoroutineScopeActivity @JvmOverloads constructor(scope: QcBaseCoroutineScope = QcUICoroutineScope())
    : AppCompatActivity(), QcBaseCoroutineScope by scope {

    override fun onDestroy() {
        super.onDestroy()

        releaseCoroutine()
    }
}