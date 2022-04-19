package com.gesecur.app.ui.vigilant.services

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gesecur.app.databinding.ActivityServicesExperimentalBinding
import com.gesecur.app.ui.vigilant.VigilantActivity
import com.gesecur.app.ui.vigilant.services.repository.Repository
import com.gesecur.app.utils.getCurrentLocation
import java.text.SimpleDateFormat
import java.util.*

class ServicesExperimental(): AppCompatActivity() {

    private lateinit var binding: ActivityServicesExperimentalBinding
    private lateinit var viewModel: ServicesViewModel

    private var vigilantId: Long = 0

    @SuppressLint("SetTextI18n", "LogNotTimber")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServicesExperimentalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarTextDay.text = "${getCurrentDay()} ${getCurrentDayNumber()} de ${getCurrentMonth()}"

        loadId()

        /**
         * @param repository: Variable para acceder al método @GET de cada vigilante en sí.
         * @param itemWorkServices: ArrayList cargado con cada uno de los datos obtenidos de la llamada @GET.
         * @param ViewModelFactory: Modelo de vista basado en la recepción de datos.
         * @param viewModel: Administrar los datos recibidos desde la llamada @GET y posteriormente almacenarlos en un MutableList según el 'result' obtenido desde dicha llamada.
         */

        val repository = Repository()
        val itemWorkServices = arrayListOf<ServicesCard>()
        val viewModelFactory = ServicesViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ServicesViewModel::class.java)
        viewModel.getServiciosVigilante(vigilantId, vigilantId)
        viewModel.myResponseGet.observe(this, androidx.lifecycle.Observer { result ->

            for (i in 0 until result.result.size) {
                itemWorkServices.add(
                    ServicesCard(
                        result
                            .result[i]
                            .contrato_servicio_id.toString(),
                        result
                            .result[i]
                            .descripcion_contrato,
                        "${result.result[i].hora_ini} - ${result.result[i].hora_fin}",
                        result
                            .result[i]
                            .descripcion_contrato_servicio,
                        "${result.result[i].fecha_ini.substring(
                                5,
                                10
                            )
                        } - ${result.result[i].fecha_fin.substring(5, 10)}",
                        result.result[i].cuadrante_id.toString(),
                        result.result[i].vigilante_id.toString()
                    )
                )
            }

            val adaptador = ServicesAdapter(itemWorkServices) {

                getCurrentLocation { location ->
                    if (location != null) {
                        val postLatitud = location.latitude.toString()
                        val postLongitud = location.longitude.toString()

                        saveData(it
                            .vigilante_id
                            .toLong(),
                            postLatitud,
                            postLongitud,
                            it.cuadrante.toLong())
                    }
                }

                startActivity(Intent(this, VigilantActivity::class.java))
            }

            binding.recyclerServicesVigilant.adapter = adaptador
            binding.recyclerServicesVigilant.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        })
    }

    /**
     * Función para obtener el código personal de cada vigilante desde AuthFragment.
     * @param vigilantId: Id personal de cada vigilante
     */

    @SuppressLint("LogNotTimber")
    private fun loadId(): Long {
        vigilantId = intent.extras!!.getString("vigilantId", null)!!.toLong()
        if (vigilantId != null) {
            Log.e("Valor de vigilantId", vigilantId.toString())
        } else {
            Log.e("ERROR", "Error al recibir código personal del vigilante")
        }
        return vigilantId
    }

    /**
     * Funciones dedicadas a obtener el Día, el Mes y el Número de dicho día respectivamente.
     * Utilizadas en la toolbar.
     */

    private fun getCurrentDay(): String {
        return SimpleDateFormat("EEEE", Locale.getDefault()).format(Date())
            .replaceFirstChar { it.toUpperCase() }
    }

    private fun getCurrentMonth(): String {
        return SimpleDateFormat("MMMM", Locale.getDefault()).format(Date())
            .replaceFirstChar { it.toUpperCase() }
    }

    private fun getCurrentDayNumber(): String {
        return SimpleDateFormat("d", Locale.getDefault()).format(Date())
            .replaceFirstChar { it.toUpperCase() }
    }

    /**
     * Función dedicada a enviar los datos en función del item elegido del RecyclerView por el vigilante.
     * Enviados a VigilantActivity y posteriormente se hará la llamada @POST.
     */

    @SuppressLint("LogNotTimber")
    private fun saveData(vigilanteId: Long, latitud: String, longitud: String, cuadranteId: Long) {
        val sharedPreferences = getSharedPreferences("datosPost", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        Log.e("DATOS", "$vigilanteId $latitud $longitud $cuadranteId")

        editor.putLong("vigilanteId", vigilanteId)
        editor.putString("lat", latitud)
        editor.putString("lon", longitud)
        editor.putLong("cuadranteId", cuadranteId)
        editor.apply()
    }
}