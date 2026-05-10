package com.turkcell.libraryappv2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turkcell.libraryappv2.data.model.BorrowRecord
import com.turkcell.libraryappv2.data.repository.AuthRepository
import com.turkcell.libraryappv2.data.repository.BorrowRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class BorrowState {
    object Idle : BorrowState()
    object Loading : BorrowState()
    object Success : BorrowState()
    data class Error(val message: String) : BorrowState()
}

data class BorrowRecordUiModel(
    val id: String,
    val statusText: String,
    val borrowDateText: String,
    val dueDateText: String
)

class BorrowViewModel : ViewModel() {
    private val borrowRepository = BorrowRepository()
    private val authRepository = AuthRepository()

    private val _borrowState = MutableStateFlow<BorrowState>(BorrowState.Idle)
    val borrowState: StateFlow<BorrowState> = _borrowState

    private val _myBorrows = MutableStateFlow<List<BorrowRecordUiModel>>(emptyList())
    val myBorrows: StateFlow<List<BorrowRecordUiModel>> = _myBorrows

    fun borrowBook(bookId: String) {
        val userId = authRepository.getCurrentUserId() ?: return
        viewModelScope.launch {
            _borrowState.value = BorrowState.Loading
            borrowRepository.borrowBook(bookId, userId)
                .onSuccess { _borrowState.value = BorrowState.Success }
                .onFailure { _borrowState.value = BorrowState.Error(it.message ?: "Kiralama başarısız") }
        }
    }

    fun loadMyBorrows() {
        val userId = authRepository.getCurrentUserId() ?: return
        viewModelScope.launch {
            borrowRepository.getMyBorrows(userId)
                .onSuccess { records ->
                    val uiModels = records.map { record ->
                        val statusText = when {
                            record.returnedAt != null -> "İade Edildi"
                            record.status == "pending" -> "Bekliyor"
                            record.status == "rejected" -> "Reddedildi"
                            else -> "Aktif"
                        }
                        BorrowRecordUiModel(
                            id = record.id,
                            statusText = statusText,
                            borrowDateText = "Alış: ${record.borrowDate.take(10)}",
                            dueDateText = "Son İade: ${record.dueDate.take(10)}"
                        )
                    }
                    _myBorrows.value = uiModels
                }
                .onFailure { }
        }
    }

    fun resetState() {
        _borrowState.value = BorrowState.Idle
    }
}
