package com.ulling.sample.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulling.sample.data.User
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
 *
 **/
class MainViewModel() : ViewModel() {
    val selected = MutableLiveData<User>()
    val users: MutableLiveData<List<User>>
        get() {
            TODO()
        }

    fun getUsers(): LiveData<List<User>> {
        return users
    }


    private fun loadUsers() {
        viewModelScope.launch(Dispatchers.IO) {

        }
        // Do an asynchronous operation to fetch users.
    }

    fun select(item: User) {
        selected.value = item
    }
}
