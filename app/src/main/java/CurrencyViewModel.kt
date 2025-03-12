import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencyViewModel(private val repository: CurrencyRepository) : ViewModel() {
    private val _exchangeRate = MutableLiveData<Double>()
    val exchangeRate: LiveData<Double> get() = _exchangeRate

    fun convertCurrency(amount: Double, from: String, to: String) {
        // Chama a API para obter a taxa de câmbio
        RetrofitInstance.api.getExchangeRates(base = from).enqueue(object : Callback<ExchangeRatesResponse> {
            override fun onResponse(
                call: Call<ExchangeRatesResponse>,
                response: Response<ExchangeRatesResponse>
            ) {
                if (response.isSuccessful) {
                    val exchangeRates = response.body()?.rates
                    val rate = exchangeRates?.get(to)
                    if (rate != null) {
                        val result = amount * rate
                        _exchangeRate.postValue(result) // Atualiza a LiveData com o valor convertido
                    } else {
                        Log.e("CurrencyConverter", "Taxa de câmbio não encontrada para a moeda: $to")
                    }
                } else {
                    Log.e("CurrencyConverter", "Erro na resposta da API: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<ExchangeRatesResponse>, t: Throwable) {
                Log.e("CurrencyConverter", "Falha na chamada da API: ${t.message}")
            }
        })
    }
}