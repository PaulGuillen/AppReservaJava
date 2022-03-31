package devpaul.business.empresaexample.modelo.ekotlinmodels

import com.google.gson.annotations.SerializedName

class MercadoPagoInstallments (
    @SerializedName("installments") val installments: Int,
    @SerializedName("installment_rate")  val installmentRate: Int,
    @SerializedName("discount_rate")  val discountRate: Int,
    @SerializedName("reimbursement_rate") val reimbursementRate: Any,
    @SerializedName("labels") val labels: ArrayList<String>,
    @SerializedName("installment_rate_collector") val installmentRateCollector: ArrayList<String>,
    @SerializedName("min_allowed_amount") val minAllowedAmount: Int,
    @SerializedName("max_allowed_amount") val maxAllowedAmount: Int,
    @SerializedName("recommended_message") val recommendedMessage: String,
    @SerializedName("installment_amount") val installmentAmount: Double,
    @SerializedName("total_amount") val totalAmount: Int,
    @SerializedName("payment_method_option_id") val paymentMethodOptionId: String

){

    override fun toString(): String {
        return installments.toString()
    }
}
