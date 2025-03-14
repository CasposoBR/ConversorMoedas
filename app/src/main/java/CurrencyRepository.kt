import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CurrencyRepository {

    // Função para obter a taxa de câmbio
    fun getExchangeRates(base: String, targetCurrency: String, callback: (Double?) -> Unit) {
        RetrofitInstance.api.getExchangeRates(base).enqueue(object :
            Callback<ExchangeRatesResponse> {
            override fun onResponse(
                call: Call<ExchangeRatesResponse>,
                response: Response<ExchangeRatesResponse>
            ) {
                if (response.isSuccessful) {
                    // Busca a taxa de câmbio para a moeda correta
                    val exchangeRate = response.body()?.rates?.get(targetCurrency)
                    callback(exchangeRate)
                } else {
                    Log.e(
                        "CurrencyRepository",
                        "Erro na resposta da API: ${response.errorBody()?.string()}"
                    )
                    callback(null)
                }
            }

            override fun onFailure(call: Call<ExchangeRatesResponse>, t: Throwable) {
                Log.e("CurrencyRepository", "Falha na chamada da API: ${t.message}")
                callback(null)
            }
        })
    }
}