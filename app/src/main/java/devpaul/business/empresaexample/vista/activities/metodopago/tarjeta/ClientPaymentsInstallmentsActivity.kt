package devpaul.business.empresaexample.vista.activities.metodopago.tarjeta

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import devpaul.business.empresaexample.R
import devpaul.business.empresaexample.controlador.provider.MercadoPagoProvider
import devpaul.business.empresaexample.modelo.Pedido
import devpaul.business.empresaexample.modelo.ekotlinmodels.MercadoPagoInstallments
import devpaul.business.empresaexample.modelo.ekotlinmodels.MercadoPagoPayment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientPaymentsInstallmentsActivity : AppCompatActivity() {

    val TAG = "ClientPaymentsInst"
    var textViewTotal: TextView? = null
    var textViewInstallmentDescription: TextView? = null
    var buttonPay: Button? = null
    var spinnerInstallments: Spinner? = null

    var mercadoPagoProvider = MercadoPagoProvider()


    var cardToken = ""
    var firstSixDigits = ""

    var gson = Gson()

    var installmentsSelected = "" // COUTA SELECCIONA 1, 10

    var paymentMethodId = ""
    var paymentTypeId = ""
    var issuerId = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_payments_installments)

        val b = intent.extras
        val result = b!!.getDouble("total")
        val test = intent.getStringExtra("idPedido")
        Log.d(TAG, "Total = $result + $test")

        cardToken = intent.getStringExtra("cardToken").toString()
        firstSixDigits = intent.getStringExtra("firstSixDigits").toString()

        textViewTotal = findViewById(R.id.textview_total)
        textViewInstallmentDescription = findViewById(R.id.textview_installments_description)
        buttonPay = findViewById(R.id.btn_pay)
        spinnerInstallments = findViewById(R.id.spinner_installments)

        getInstallments()


    }

    private  fun createPayment(){

        val idPedido = intent.getStringExtra("idPedido")
        val b = intent.extras
        val total = b!!.getDouble("total")



    }

    private fun getInstallments() {
        val b = intent.extras
        val result = b!!.getDouble("total")
        mercadoPagoProvider.getInstallments(firstSixDigits, result.toString())?.enqueue(object:
            Callback<JsonArray> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>
            ) {

                if (response.body() != null) {

                    val jsonInstallments = response.body()!!.get(0).asJsonObject.get("payer_costs").asJsonArray

                    val type = object: TypeToken<ArrayList<MercadoPagoInstallments>>() {}.type
                    val installments = gson.fromJson<ArrayList<MercadoPagoInstallments>>(jsonInstallments, type)

                    Log.d(TAG, "Response: $response")
                    Log.d(TAG, "installments: $jsonInstallments")
                    textViewTotal?.text = "${result} Soles"

                    paymentMethodId = response.body()?.get(0)?.asJsonObject?.get("payment_method_id")?.asString!!
                    paymentTypeId = response.body()?.get(0)?.asJsonObject?.get("payment_type_id")?.asString!!
                    issuerId = response.body()?.get(0)?.asJsonObject?.get("issuer")?.asJsonObject?.get("id")?.asString!!


                    val arrayAdapter = ArrayAdapter(this@ClientPaymentsInstallmentsActivity, android.R.layout.simple_dropdown_item_1line, installments)
                    spinnerInstallments?.adapter = arrayAdapter
                    spinnerInstallments?.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, l: Long) {
                            installmentsSelected = "${installments[position].installments}"
                            Log.d(TAG, "Coutas seleccionadas: $installmentsSelected")
                            textViewInstallmentDescription?.text = installments[position].recommendedMessage

                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {

                        }
                    }
                }

            }

            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Toast.makeText(this@ClientPaymentsInstallmentsActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }

        })
    }

}