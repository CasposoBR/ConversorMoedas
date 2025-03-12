import android.util.Log


class CurrencyRepository {
    suspend fun getRates(base: String): ExchangeRatesResponse? {
        return try {
            val response = RetrofitInstance.api.getExchangeRates(base)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("CurrencyConverter", "Erro na resposta da API: ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("CurrencyConverter", "Erro ao chamar a API: ${e.message}")
            null
        }
    }
}