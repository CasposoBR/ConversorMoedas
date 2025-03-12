import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyApi {
    @GET("v6/YOUR-API-KEY/latest/{base}")
    fun getExchangeRates(@Path("base") base: String): Call<ExchangeRatesResponse>

}




