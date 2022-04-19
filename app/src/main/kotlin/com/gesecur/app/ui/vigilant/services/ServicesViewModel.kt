package com.gesecur.app.ui.vigilant.services

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.getOrElse
import com.gesecur.app.data.gesecur.responses.BaseResponse
import com.gesecur.app.domain.models.User
import com.gesecur.app.domain.repositories.user.UserRepository
import com.gesecur.app.domain.repositories.user.UserRepositoryImpl
import com.gesecur.app.ui.vigilant.services.model.Services
import com.gesecur.app.ui.vigilant.services.repository.Repository
import com.gesecur.app.ui.vigilant.services.repository.RepositoryImpl
import kotlinx.coroutines.launch
import retrofit2.Response
import java.security.PrivateKey

open class ServicesViewModel(private val repository: Repository): ViewModel() {

    val myResponseGet: MutableLiveData<Services> = MutableLiveData()
    val myResponsePost: MutableLiveData<Response<Long>> = MutableLiveData()

    fun getServiciosVigilante(userId: Long, vigilantId: Long) {
        viewModelScope.launch {
            val response = repository.getServiciosVigilante(userId, vigilantId)
            myResponseGet.value = response
        }
    }

    /**fun getCurrentUser(): Long {
        return currentUser!!.id
    }*/
}