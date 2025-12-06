package com.smartbusiness.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartbusiness.data.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailsViewModel @Inject constructor(
    private val repository: GroupRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GroupDetailsUiState())
    val uiState: StateFlow<GroupDetailsUiState> = _uiState.asStateFlow()

    init {
        loadGroupDetails("123")
    }

    private fun loadGroupDetails(groupId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val group = repository.getGroupDetails(groupId)
                _uiState.update { it.copy(isLoading = false, group = group) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = e.message ?: "Unknown error")
                }
            }
        }
    }

    fun onBackClicked() {
        _uiState.update { it.copy(lastAction = "Back clicked") }
    }

    fun onMediaDocsLinkClicked() {
        _uiState.update { it.copy(lastAction = "Media, Docs, Link clicked") }
    }

    fun onAddMembersClicked() {
        _uiState.update { it.copy(lastAction = "Add Members clicked") }
    }

    fun onMemberClicked(memberId: String) {
        _uiState.update { it.copy(lastAction = "Member $memberId clicked") }
    }

    fun onViewAllMembersClicked() {
        _uiState.update { it.copy(lastAction = "View all Members clicked") }
    }

    fun onDeleteGroupClicked() {
        _uiState.update { it.copy(lastAction = "Delete Group clicked") }
    }

    fun clearAction() {
        _uiState.update { it.copy(lastAction = null) }
    }
}
