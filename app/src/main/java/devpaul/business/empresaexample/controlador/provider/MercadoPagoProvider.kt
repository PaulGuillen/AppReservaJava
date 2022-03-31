package devpaul.business.empresaexample.controlador.provider

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import devpaul.business.empresaexample.controlador.api.MercadoPagoApiRoutes
import devpaul.business.empresaexample.controlador.routes.MercadoPagoRoutes
import devpaul.business.empresaexample.modelo.ekotlinmodels.MercadoPagoCardTokenBody
import retrofit2.Call

class MercadoPagoProvider {

    var mercadoPagoRoutes: MercadoPagoRoutes? = null

    init {
        val api = MercadoPagoApiRoutes()
        mercadoPagoRoutes = api.getMercadoPagoRoutes()
    }

    fun getInstallments(bin: String, amount: String): Call<JsonArray>? {
        return mercadoPagoRoutes?.getInstallments(bin, amount)
    }


    fun createCardToken(mercadoPagoCardTokenBody: MercadoPagoCardTokenBody): Call<JsonObject>? {
        return mercadoPagoRoutes?.createCardToken(mercadoPagoCardTokenBody)
    }

}