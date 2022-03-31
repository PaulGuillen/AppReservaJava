package devpaul.business.empresaexample.controlador.routes

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import devpaul.business.empresaexample.modelo.ekotlinmodels.MercadoPagoCardTokenBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MercadoPagoRoutes {

    @GET("v1/payment_methods/installments?access_token=TEST-4432208581970729-032820-faea52febd47400c9dfa33f9f010bf3a-666034814")
    fun getInstallments(@Query("bin") bin: String, @Query("amount") amount: String): Call<JsonArray>

    @POST("v1/card_tokens?public_key=TEST-78564444-8265-4de2-a0d1-c66306bc06d1")
    fun createCardToken(@Body body : MercadoPagoCardTokenBody) : Call<JsonObject>
}