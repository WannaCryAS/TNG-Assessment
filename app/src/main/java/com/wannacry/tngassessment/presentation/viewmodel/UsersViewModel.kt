package com.wannacry.tngassessment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wannacry.tngassessment.domain.data.User
import com.wannacry.tngassessment.domain.usecase.GetUserUseCase
import com.wannacry.tngassessment.presentation.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class UsersViewModel(private val useCase: GetUserUseCase) : ViewModel() {

    private val _state = MutableStateFlow<UiState>(UiState.Loading)
    val state: StateFlow<UiState> = _state

    private val _usersUi = MutableStateFlow<List<User>>(emptyList())
    val usersUi: StateFlow<List<User>> = _usersUi

    init {
        getUsers()
    }

    fun getUsers(onComplete: (() -> Unit)? = null, isRefreshing: Boolean = false) {

        if (!isRefreshing) {
            _state.value = UiState.Loading
        }

        viewModelScope.launch {
            //delay just to show loading state
            delay(2000)

            useCase.execute()
                .catch { error ->
                    _state.value = UiState.Error(error.message ?: "Try again")
                }
                .collect { list ->
                    if (list.isEmpty()) {
                        _state.value = UiState.Error("No user found")
                    } else {
                        _usersUi.value = list
                        _state.value = UiState.Success(list)
                    }
                }

            onComplete?.invoke()
        }
    }

    fun sortByName(ascending: Boolean = true) {
        val sorted = if (ascending) {
            _usersUi.value.sortedBy { it.name?.lowercase() }
        } else {
            _usersUi.value.sortedByDescending { it.name?.lowercase() }
        }
        _usersUi.value = sorted
        _state.value = UiState.Success(sorted)
    }
}
