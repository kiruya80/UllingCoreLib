package com.ulling.lib.core.listener

import android.view.View
import com.ulling.lib.core.utils.QcLog
import java.util.*


class OnHasTermClickListener(private val click: (view: View) -> Unit) : View.OnClickListener {

    override fun onClick(v: View) {
        QcLog.e("onClick ============= " + v.tag)
        val current = Calendar.getInstance().timeInMillis
//        val clickTerm = current - App.get().LAST_CLICK_EVENT_TIME
//        QcLog.e("clickTerm LAST_CLICK_EVENT_TIME ============ " + clickTerm + " ," + App.get().CLICK_EVENT_TERM
//                + " === " + App.get().LAST_CLICK_EVENT_TIME )
//
//        if (clickTerm < App.get().CLICK_EVENT_TERM) {
//            return
//        }
//
//        App.get().LAST_CLICK_EVENT_TIME = current
        click(v)
    }
}

fun View.setOnHasTermClickListener(block: (view: View) -> Unit) {
    setOnClickListener(OnHasTermClickListener(block))
}
