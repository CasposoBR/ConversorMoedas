import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("latest") // Endpoint correto da API
    fun getExchangeRates(
        @Query("base") base: String // Parâmetro para a moeda base
    ): Call<ExchangeRatesResponse>
}


