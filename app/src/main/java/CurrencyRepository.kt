import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CurrencyRepository {

    // Função para obter a taxa de câmbio
    fun getExchangeRates(base: String, callback: (Double?) -> Unit) {
        RetrofitInstance.api.getExchangeRates(base).enqueue(object :
            Callback<ExchangeRatesResponse> {
            override fun onResponse(
                call: Call<ExchangeRatesResponse>,
                response: Response<ExchangeRatesResponse>
            ) {
                // Verifica se a resposta foi bem-sucedida
                if (response.isSuccessful) {
                    // Extraímos a taxa de câmbio para a moeda desejada
                    val exchangeRate = response.body()?.rates?.get("BRL") // Ou a moeda desejada
                    callback(exchangeRate)  // Retorna o resultado usando o callback
                } else {
                    Log.e("CurrencyRepository", "Erro na resposta da API: ${response.errorBody()}")
                    callback(null)  // Em caso de erro, retornamos null
                }
            }

            override fun onFailure(call: Call<ExchangeRatesResponse>, t: Throwable) {
                Log.e("CurrencyRepository", "Falha na chamada da API: ${t.message}")
                callback(null)  // Se a chamada falhar, retornamos null
            }
        })
    }
}