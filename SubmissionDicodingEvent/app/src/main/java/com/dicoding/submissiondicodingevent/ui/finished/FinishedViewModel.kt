package com.dicoding.submissiondicodingevent.ui.finished

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submissiondicodingevent.data.remote.event.EventDetail
import com.dicoding.submissiondicodingevent.data.remote.event.EventResponse
import com.dicoding.submissiondicodingevent.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinishedViewModel : ViewModel() {

    private val _finishedEvents = MutableLiveData<List<EventDetail>>()
    val finishedEvents: LiveData<List<EventDetail>> = _finishedEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun getFinishedEvents() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListEvent(active = 0)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error!!) {
                        _finishedEvents.value = responseBody.listEvents!!
                    } else {
                        _error.value = responseBody?.message ?: "Unknown error"
                    }
                } else {
                    _error.value = "Response not successful"
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                _error.value = t.message
            }
        })
    }
}
