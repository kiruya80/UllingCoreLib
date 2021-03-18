package com.ulling.lib.core.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ulling.lib.core.utils.QcLog
import com.ulling.lib.core.utils.QcUtils

/**
 *
 *
 * Class:
 * Created by KILHO on 2021-03-11 오후 3:21
 *
 * @version 1.0.0
 *
 * Description:
 *
 *
 **/
abstract class QcSimpleFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QcLog.i("onCreate")

        arguments?.let {
            QcUtils.getArgumentsAll(it)
        }
    }


    /**
     * Activity에서 Fragment를 모두 생성하고 난다음 호출 된다.
     * Activity의 onCreate()에서 setContentView()한 다음이라고 생각 하면 쉽게 이해 될것 같다.
     * 여기서 부터는 ui변경작업이 가능하다.
     *
     *
     *
     *
     *
     *
     * Activity의 onCreate()를 완료되고 fragment의 View 생성이 완료했을때 호출됩니다.
     * Activity와 Fragment의 View가 모두 생성된 시점이라 findViewById()를 사용하여
     * !! View 객체에 접근하는게 가능합니다.
     * inflate된 레이아웃 내에서 R.java에 할당되어있는 주어진 ID를 가지고 view를 찾습니다.
     * !! 또한 Context객체를 요구하는 객체를 초기화 할 수 있습니다.
     *
     *
     * 유저 인터페이스와 관련된 처리는 onActivityCreated 메소드에서 해주어야 합니다.
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        QcLog.e("onActivityCreated == ")
    }

    /**
     * 액티비티가 사용자에게 표시되기 직전에 호출
     * 액티비티가 전경으로 나오면 onResume()
     * 액티비티가 숨겨지면 onStop()
     *
     *
     * Fragment가 사용자에게 보여질때 호출되며 아직 사용자와 상호작용은 불가능한 상태입니다.
     * (fragment가 속한 activity가 start된거랑 관련있습니다.)
     */
    override fun onStart() {
        super.onStart()
        QcLog.i("onStart == ")
    }

    /**
     * 액티비티가 시작되고 사용자와 상호 작용하기 직전에 호출
     *
     *
     * fragment가 사용자에게 보여지고 실행 중일때 호출되며 사용자와 상호작용할 수 있는 상태입니다. (fragment가 속한 activity가 resume된거랑 관련있습니다.)
     *
     *
     * 보통 onResume에서 자원을 할당하고 onPause에서 자원을 해제해줍니다.
     */
    override fun onResume() {
        super.onResume()
        QcLog.i("onResume == ")
    }

    /**
     * Fragment를 종료한다는 첫 번째 신호
     * 저장되어야 할 것들을 저장 시켜야함
     *
     *
     * Activity가 pause되어 fragment가 더이상 사용자와 상호작용하지 않을 때이다.
     */
    override fun onPause() {
        super.onPause()
        QcLog.i("onPause == ")
    }

    /**
     * 액티비티가 더 이상 사용자에게 표시되지 않게 되면 호출
     * 액티비티가 다시 사용자와 상호 작용하면 onRestart()
     *
     *
     * Activity가 stop되었거나 fragment의 operation이 activity에 의해 수정되었을 경우로 fragment가 더이상 사용자에게 보여지지 않을 때입니다.
     */
    override fun onStop() {
        super.onStop()
        QcLog.i("onStop == ")
    }

    /**
     * 사용자 UI를 제거하는 시점에서 호출
     * View 리소스를 해제 해주는 역할 구현
     */
    override fun onDestroyView() {
        super.onDestroyView()
        QcLog.i("onDestroyView == ")
    }

    /**
     * 액티비티가 사라지면 onDestroy()
     *
     *
     * fragment를 더 이상 사용하지 않을때 호출되며 activity와 연결이 끊어진 상태는 아니지만 fragment는 동작을 하지 않는 상태입니다.
     * 시스템에서 onDestroy가 항상 호출되는 것을 보장해주지 않습니다.
     */
    override fun onDestroy() {
        super.onDestroy()
        QcLog.i("onDestroy == ")
    }

    /**
     * Fragment가 Activity에 제거 될 때 호출f
     *
     *
     * fragment가 activity와의 연결이 끊어지기 전에 호출되며 fragment의 view hierarchy가 더 이상 존재하지 않게 됩니다.
     * 부모 activity가 full 라이프사이클을 완료하지 않고 종료되었다면 onDetach()는 호출되지 않습니다.
     */
    override fun onDetach() {
        super.onDetach()
        QcLog.i("onDetach == ")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        QcLog.i("onActivityResult == ")
    }
}