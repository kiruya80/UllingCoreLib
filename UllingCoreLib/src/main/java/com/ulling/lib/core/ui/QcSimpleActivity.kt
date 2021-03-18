package com.ulling.lib.core.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ulling.lib.core.utils.QcLog
import com.ulling.lib.core.utils.QcUtils

/**
 *
 *
 * Class:
 * Created by KILHO on 2021-03-11 오후 2:39
 *
 * @version 1.0.0
 *
 * Description:
 *
 * 베이스 액티비티
 *
 **/
abstract class QcSimpleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent?.let {
            QcUtils.getIntentAll(it)
        }
    }


    /**
     *
     */
    override fun onRestart() {
        super.onRestart()
        QcLog.i("onRestart == ")
    }

    /**
     * 액티비티가 사용자에게 표시되기 직전에 호출
     * 액티비티가 전경으로 나오면 onResume()
     * 액티비티가 숨겨지면 onStop()
     */
    override fun onStart() {
        super.onStart()
        QcLog.i("onStart == ")
    }

    /**
     * 액티비티가 시작되고 사용자와 상호 작용하기 직전에 호출
     */
    override fun onResume() {
        super.onResume()
        QcLog.i("onResume == ")
    }

    /**
     * 시스템이 다른 액티비티를 재개하기 직전에 호출
     * CPU를 소모하는 기타 작업들을 중단하는 등 여러 가지 용도에 사용
     * 이 메서드는 무슨 일을 하든 매우 빨리 끝내야함
     */
    override fun onPause() {
        super.onPause()
        QcLog.i("onPause == ")
    }

    /**
     * 액티비티가 더 이상 사용자에게 표시되지 않게 되면 호출
     * 액티비티가 다시 사용자와 상호 작용하면 onRestart()
     */
    override fun onStop() {
        super.onStop()
        QcLog.i("onStop == ")
    }

    /**
     * 액티비티가 사라지면 onDestroy()
     */
    override fun onDestroy() {
        super.onDestroy()
        QcLog.i("onDestroy == ")
    }

    /**
     * //    int REQUEST_ACT = 10;
     * 호출
     * //    Intent intent = new Intent(Activity_A.this, Activity_B.class);
     * //    startActivityForResult(intent, REQUEST_ACT);
     *
     *
     * 돌아오기
     * //    Intent intent = new Intent();
     * //    intent.putExtra("result_msg","결과가 넘어간다 얍!");
     * //    setResult(RESULT_OK, intent);
     * //    finish();
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        QcLog.i("onActivityResult == ")

    }
}