package com.smartbusiness.presentation

import com.smartbusiness.data.model.GroupDetails


data class GroupDetailsUiState(
    val isLoading: Boolean = false,
    val group: GroupDetails? = null,
    val errorMessage: String? = null,
    val lastAction: String? = null
)