package com.wannacry.tngassessment.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wannacry.tngassessment.domain.data.User
import com.wannacry.tngassessment.domain.usecase.GetUserUseCase
import com.wannacry.tngassessment.presentation.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsersViewModel(private val useCase: GetUserUseCase) : ViewModel() {
    private val _state = MutableStateFlow<UiState>(UiState.Loading)
    val state: StateFlow<UiState> = _state

    private val _users = MutableLiveData<List<User>>(emptyList())
    val users: LiveData<List<User>> = _users

    init {
        getUsers()
    }

    fun getUsers(onComplete: (() -> Unit)? = null) {
        _state.value = UiState.Loading
        viewModelScope.launch {
            delay(2000)
            try {
                val userList = useCase.execute()

                    if (userList.isEmpty()) {
                        _state.value =  UiState.Error("No user found")
                } else {
                        _state.value = UiState.Success(userList)
                        _users.value = userList
                }
            } catch (t: Throwable) {
                _state.value = UiState.Error(t.message ?: "Try again")
            } finally {
                onComplete?.invoke()
            }
        }
    }

    fun sortByName(ascending: Boolean = true) {

        val sorted = if (ascending) {
            users.value?.sortedBy { it.name?.lowercase() }
        } else {
            users.value?.sortedByDescending { it.name?.lowercase() }
        }
        if (sorted != null) {
            _state.update { UiState.Success(sorted) }
        }
    }
}