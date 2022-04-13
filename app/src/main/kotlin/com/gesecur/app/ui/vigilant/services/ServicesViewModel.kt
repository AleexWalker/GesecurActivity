package com.gesecur.app.ui.vigilant.services

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gesecur.app.data.gesecur.responses.BaseResponse
import com.gesecur.app.ui.vigilant.services.model.Services
import com.gesecur.app.ui.vigilant.services.repository.Repository
import com.gesecur.app.ui.vigilant.services.repository.RepositoryImpl
import kotlinx.coroutines.launch
import retrofit2.Response

class ServicesViewModel (private val repository: Repository): ViewModel() {

    val myResponseGet: MutableLiveData<Services> = MutableLiveData()
    val myResponsePost: MutableLiveData<Response<Long>> = MutableLiveData()

    fun getServiciosVigilante(userId: Long, vigilantId: Long) {
        viewModelScope.launch {
            val response = repository.getServiciosVigilante(userId, vigilantId)
            myResponseGet.value = response
        }
    }

    /**fun postServicio(vigilante_id: Long, lat: Long, lon: Long, cuadrante_id: Long) {
        viewModelScope.launch {
            val response = repository.postServicio(vigilante_id, lat, lon, cuadrante_id)
            myResponseServicio.value = response
        }
    }*/
}