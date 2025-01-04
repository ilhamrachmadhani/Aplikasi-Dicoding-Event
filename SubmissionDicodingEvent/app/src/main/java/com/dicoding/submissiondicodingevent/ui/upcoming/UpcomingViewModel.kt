package com.dicoding.submissiondicodingevent.ui.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submissiondicodingevent.data.remote.event.EventDetail
import com.dicoding.submissiondicodingevent.data.remote.event.EventResponse
import com.dicoding.submissiondicodingevent.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingViewModel : ViewModel() {

    private val _upcomingEvents = MutableLiveData<List<EventDetail>>()
    val upcomingEvents: LiveData<List<EventDetail>> = _upcomingEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun getUpcomingEvents() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListEvent(active = 1)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error!!) {
                        _upcomingEvents.value = responseBody.listEvents!!
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
