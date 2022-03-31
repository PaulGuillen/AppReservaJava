package devpaul.business.empresaexample.controlador.api


import devpaul.business.empresaexample.controlador.routes.MercadoPagoRoutes
import retrofit2.Retrofit

class MercadoPagoApiRoutes {

    val API_URL = "https://api.mercadopago.com/"
    val retrofit = RetrofitClient()




    fun getMercadoPagoRoutes(): MercadoPagoRoutes {
        return retrofit.getClient(API_URL).create(MercadoPagoRoutes::class.java)
    }

}