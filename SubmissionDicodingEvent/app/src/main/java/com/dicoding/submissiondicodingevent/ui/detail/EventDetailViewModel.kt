package com.dicoding.submissiondicodingevent.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.submissiondicodingevent.data.remote.event.EventDetail
import com.dicoding.submissiondicodingevent.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class EventDetailViewModel() : ViewModel() {

    private val _eventDetail = MutableLiveData<EventDetail?>()
    val eventDetail: LiveData<EventDetail?> = _eventDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun getEventDetail(eventId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().getEventById(eventId).awaitResponse()
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error!!) {
                        _eventDetail.value = responseBody.event
                    } else {
                        _error.value = responseBody?.message ?: "Unknown error"
                    }
                } else {
                    _error.value = "Response not successful: ${response.code()}"
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _error.value = e.message
            }
        }
    }


}
