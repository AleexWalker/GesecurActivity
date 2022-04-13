package com.gesecur.app.ui.vigilant.services.model

import com.gesecur.app.ui.vigilant.services.models.ServicesVigilante
import kotlinx.serialization.SerialName

data class Services(
    @SerialName("status") val status: Long,
    @SerialName("result") val result: ArrayList<ServicesVigilante>,
)