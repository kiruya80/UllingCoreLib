package com.ulling.lib.core.ui

import android.content.Intent
import com.ulling.lib.core.utils.QcLog

/**
 *
 *
 * Class:
 * Created by KILHO on 2021-03-11 오후 2:32
 *
 * @version 1.0.0
 *
 * Description:
 *
 * 액티비티 프레그먼트 옵션 인터페이스
 **/
interface QcInOption {
    /**
     * 인텐트 가져오기
     *
     * @param intent
     */
    fun optGetIntent(intent: Intent?)

    fun optOnActivityResultOk(requestCode: Int, data: Intent?) {
        QcLog.i("RESULT_OK requestCode == $requestCode")
//        if (requestCode == REQUEST_ACT) {
//            String resultMsg = data.getStringExtra("result_msg");
//        }
    }

    fun optOnActivityResultCancle() {
        QcLog.i("RESULT_CANCELED == ")
    }
}