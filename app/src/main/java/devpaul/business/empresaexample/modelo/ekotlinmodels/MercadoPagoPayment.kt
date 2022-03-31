package devpaul.business.empresaexample.modelo.ekotlinmodels

import com.google.gson.annotations.SerializedName
import devpaul.business.empresaexample.modelo.Pedido

class MercadoPagoPayment(
    @SerializedName("order") val order: String,
    @SerializedName("token") val token: String,
    @SerializedName("description") val description: String,
    @SerializedName("payment_method_id") val paymentMethodId: String,
    @SerializedName("payment_type_id") val paymentTypeId: String,
    @SerializedName("issuer_id") val issuerId: String,
    @SerializedName("payer") val payer: Payer,
    @SerializedName("transaction_amount") val transactionAmount: Double,
    @SerializedName("installments") val installments: Int,


) {
}