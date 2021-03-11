package com.ulling.lib.core.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.ulling.lib.core.base.QcBaseApplication
import com.ulling.lib.core.utils.QcLog

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
abstract class QcBaseFragment : Fragment(), QcInNeed, QcInOption {
    val TAG: String = javaClass.simpleName
    var APP_NAME: String? = null

    var qCon: Context? = null
//    var start = false

    var baseActivity: QcBaseActivity? = null
    var sectionPosition = 0
    val ARG_SECTION_POSITION = "ARG_SECTION_POSITION"

    /**
     * 레이아웃 내 설정한 아이디들을 바인딩하는 클래스
     *
     *
     * 프레그먼트와 맞는 뷰데이터바인딩 클래스로 형변환 해야함
     */
    protected var rootViewBinding: ViewDataBinding? = null

    protected abstract fun optGetArgument(arguments: Bundle?, savedInstanceState: Bundle?)

    /**
     * Lifecycle
     *
     * onAttach() > onCreateView() > onStart() > onStop() > onResume() > onPause() > onDestroyView() > onDetach()
     */
    /**
     * Lifecycle
     *
     * onAttach() > onCreateView() > onStart() > onStop() > onResume() > onPause() > onDestroyView() > onDetach()
     */
    /**
     * Fragment가 생성된 시점에 호출됩니다.
     * Activity의 onCreate메소드가 아직 완료된 시점이 아니라서 유저 인터페이스와 관련있는 것을 제외한 Fragment에서 사용되는 리소스들이 초기화됩니다.
     * ui관련 작업은 할 수 없다.
     * 유저 인터페이스와 관련된 처리는 onActivityCreated 메소드에서 해주어야 합니다.!!
     *
     *
     *
     *
     * Fragment가 paused 또는 stop되었다가
     * 다시 resume되었을 때 유지하고 싶은 Fragment의 컴포넌트들를 여기서 초기화 해주어야 합니다.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QcLog.i("onCreate")

        if (activity != null)
            qCon = activity?.applicationContext

        needInitToOnCreate()
        optGetArgument(arguments, savedInstanceState)
        needResetData()
    }


    /**
     * Layout을 inflater을하여 View작업을 하는곳이다.
     *
     *
     * onStop / onDestroyView 에서 돌아오는 경우 호출됨
     *
     *
     * Fragment의 유저 인터페이스가 화면에 그려지는 시점에 호출됩니다. (사용자 UI를 처음 그리는 시점에서 호출)
     * XML 레이아웃을 inflate하여 Fragment를 위한 View를 생성하고 Fragment 레이아웃의 root에 해당되는 View를 Activity에게 리턴해야 합니다.
     * inflate란 XML 레이아웃에 정의된 뷰나 레이아웃을 읽어서 메모리상의 view 객체를 생성해주는 겁니다.
     * 여기서 view를 리턴했다면, view가 release될때 onDestroyView()가 호출됩니다.
     * 유저 인터페이스 없는 Fragment의 경우에는 null을 리턴하면 됩니다.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        QcLog.i("onCreateView == ")
        if (rootViewBinding == null)
            rootViewBinding = DataBindingUtil.inflate<ViewDataBinding>(inflater!!, needGetLayoutId(), container, false)
        return rootViewBinding?.getRoot()
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
        //        optGetArgument();
        if (rootViewBinding != null) {
            baseActivity = activity as QcBaseActivity?
            needUIBinding()
            needUIEventListener()
            needSubscribeUiFromViewModel()
        }
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
        QcBaseApplication.CLICK_LAST_RUN_TIME = 0
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
        needResetData()
        needSubscribeUiClear()
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
        if (resultCode == AppCompatActivity.RESULT_OK) {
            optOnActivityResultOk(requestCode, data)
        } else {
            optOnActivityResultCancle()
        }
    }
}