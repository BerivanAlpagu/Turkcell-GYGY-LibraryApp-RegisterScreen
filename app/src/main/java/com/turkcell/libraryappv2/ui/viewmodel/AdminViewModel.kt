package com.turkcell.libraryappv2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turkcell.libraryappv2.data.model.AdminBorrowRecord
import com.turkcell.libraryappv2.data.repository.BorrowRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdminViewModel : ViewModel() {
    private val borrowRepository = BorrowRepository()

    private val _pendingRequests = MutableStateFlow<List<AdminBorrowRecord>>(emptyList())
    val pendingRequests: StateFlow<List<AdminBorrowRecord>> = _pendingRequests

    fun loadPendingRequests() {
        viewModelScope.launch {
            borrowRepository.getPendingBorrows()
                .onSuccess { _pendingRequests.value = it }
        }
    }

    fun approveRequest(recordId: String) {
        viewModelScope.launch {
            borrowRepository.approveBorrow(recordId)
                .onSuccess { loadPendingRequests() }
        }
    }

    fun rejectRequest(recordId: String, bookId: String) {
        viewModelScope.launch {
            borrowRepository.rejectBorrow(recordId, bookId)
                .onSuccess { loadPendingRequests() }
        }
    }
}
