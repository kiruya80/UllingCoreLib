package com.ulling.sample.ui.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ulling.sample.ui.ui.User

class MainViewModel : ViewModel() {
    val selected = MutableLiveData<User>()
      val users: MutableLiveData<List<User>>
          get() {
              TODO()
          }

    fun getUsers(): LiveData<List<User>> {
        return users
    }


    private fun loadUsers() {
        // Do an asynchronous operation to fetch users.
    }

    fun select(item: User) {
        selected.value = item
    }
}
