package com.wannacry.tngassessment.presentation

import com.wannacry.tngassessment.domain.data.User

interface UiState {
    object Loading : UiState
    data class Success(val users: List<User>): UiState
    data class Error(val errorMessage: String): UiState
}