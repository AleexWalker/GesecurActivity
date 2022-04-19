package com.gesecur.app.ui.vigilant.services.repository

import com.gesecur.app.ui.vigilant.services.api.RetrofitInstance
import com.gesecur.app.ui.vigilant.services.model.Services
import retrofit2.Response

class Repository(): RepositoryImpl {

    suspend fun getServiciosVigilante(userId: Long, vigilantId: Long): Services {
        return RetrofitInstance.api.getServiciosVigilante(userId, vigilantId)
    }

    suspend fun postServicio(vigilantId: Long, lat: Long, lon: Long, cuadrante_id: Long): Response<Long> {
        return RetrofitInstance.api.postServicio(vigilantId, lat, lon, cuadrante_id)
    }
}
