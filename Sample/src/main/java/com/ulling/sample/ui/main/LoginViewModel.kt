package com.ulling.sample.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulling.lib.core.utils.QcLog
import com.ulling.sample.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *
 *
 * Class:
 * Created by 19001662 on 2021-03-12 오후 2:23
 *
 * @version 1.0.0
 *
 * Description:
 *
 * 뷰모델
 * ㄴ UI 컨트롤러의 데이터를 캡슐화하여 구성이 변경되어도 데이터를 유지하는 것
 * ViewModel 사용 시 ViewModel에 액티비티, 프래그먼트, 뷰에 대한 컨텍스트를 저장해서는 안됩니다.
 * 액티비티가 재생성 될 때, ViewModel은 액티비티 수명주기 외부에 존재하기 때문에 UI 컨텍스트를 ViewModel에 저장한다면 메모리 릭을 발생시키는 직접적인 원인이 될 수 있습니다.
 * 다만 Application 컨택스트를 저장하는 것은 문제가 되지 않습니다.
 * Application 컨택스트는 전체 앱의 수명주기를 의미하기 때문에 메모리 릭에 영향을 주지 않으며 이런 용도를 위해 AndroidViewModel 클래스를 제공합니다.
 *
 *
 *
 *  코루틴 설명 구글
 *  ㄴ https://developer.android.com/kotlin/coroutines?hl=ko
 *
 *   https://jaejong.tistory.com/61
 *
 * iewModel 레이어에서 View와 통신하려면 앱 아키텍처 가이드에서 권장하는 대로 LiveData를 사용합니다.
 * 이 패턴을 따르면 ViewModel의 코드가 기본 스레드에서 실행되므로 MutableLiveData의 setValue() 함수를 직접 호출할 수 있습니다.
 **/
class LoginViewModel( ) : ViewModel() {
    var loginRepository: LoginRepository ?=null

    fun init() {
        val responseParser: LoginResponseParser = LoginResponseParser()
        loginRepository = LoginRepository(responseParser)
    }
    /**
     * 코루틴 예제
     *
     * launch(Dispatchers.IO) 들어가기 전에는 메인 스레드, 들어가면  I/O 작업용으로 예약된 스레드
     *
     * launch가 Dispatchers.IO 매개변수를 사용하지 않습니다. Dispatcher를 launch에 전달하지 않으면
     * viewModelScope에서 실행된 코루틴은 기본 스레드에서 실행됩니다.
     */
    fun login(username: String, token: String) {
        QcLog.e("login ==== ")
        // 1.
        // Create a new coroutine to move the execution off the UI thread
        viewModelScope.launch(Dispatchers.IO) {
            QcLog.e("login viewModelScope.launch ==== ")
            val jsonBody = "{ username: \"$username\", token: \"$token\"}"
            loginRepository?.makeLoginRequest(jsonBody)
        }
    }

    /**
     * makeLoginRequest가 실행을 기본 스레드 외부로 이동하므로,
     * 이제 login 함수의 코루틴이 기본 스레드에서 실행될 수 있습니다.
     *
     * makeLoginRequestC 들어가기 전에는 메인 스레드
     * makeLoginRequestC 들어가서 withContext(Dispatchers.IO) 안에서 I/O 작업용으로 예약된 스레드
     * makeLoginRequestC 돌아오면  메인  UI스레드로 와서 업데이트 등등
     *
     */
    fun loginC(username: String, token: String) {
        QcLog.e("loginC ==== ")
        // 2. makeLoginRequest가 실행을 기본 스레드 외부로 이동하므로, 이제 login 함수의 코루틴이 기본 스레드에서 실행될 수 있습니다.
        // Create a new coroutine on the UI thread
        viewModelScope.launch {
            QcLog.e("loginC viewModelScope.launch ==== ")
            val jsonBody = "{ username: \"$username\", token: \"$token\"}"

            // Make the network call and suspend execution until it finishes
            val result = loginRepository?.makeLoginRequestC(jsonBody)
            QcLog.e("result === " + result.toString())

            // Display result of the network request to the user
            when (result) {
                is ResultLogIn.Success<LoginResponse> -> {
                    // Happy path
                }
                else -> {
                    // Show error in UI
                }
            }
        }
    }
}
