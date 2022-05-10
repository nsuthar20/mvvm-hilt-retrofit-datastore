package com.apps.mvvm.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.mvvm.data.network.Resource
import com.apps.mvvm.data.repository.AuthRepository
import com.apps.mvvm.data.response.AuthResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _authResponse: MutableLiveData<Resource<AuthResponse>> = MutableLiveData()
    val authResponse: LiveData<Resource<AuthResponse>>
        get() = _authResponse

    fun doLogin(context: Context, username: String?, password: String?) = viewModelScope.launch {
        _authResponse.value = Resource.Loading
        _authResponse.value = repository.doLogin(
            HashMap(buildMap {
                this["username"] = username
                this["password"] = password
            })
        )
    }

    suspend fun saveAuthKey(authToken: String) = viewModelScope.launch {
        repository.saveAuthKey(authToken)
    }
}