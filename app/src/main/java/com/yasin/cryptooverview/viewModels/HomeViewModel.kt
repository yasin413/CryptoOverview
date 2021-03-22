package com.yasin.cryptooverview.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasin.cryptooverview.RequestStatus
import com.yasin.cryptooverview.models.CryptoCurrency
import com.yasin.cryptooverview.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    val currencies = repository.currencies

    private val _status = MutableLiveData<RequestStatus>()
    val status: LiveData<RequestStatus>
        get() = _status

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    init {
        getData()
    }

    fun getData() {
        _status.value = RequestStatus.Loading
        viewModelScope.launch {
            _status.postValue(repository.refreshData())
        }
    }
}