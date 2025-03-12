
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
        repository.getExchangeRates(from).enqueue(object : Callback<ExchangeRatesResponse> {
            override fun onResponse(call: Call<ExchangeRatesResponse>, response: Response<ExchangeRatesResponse>) {
                if (response.isSuccessful) {
                    val rates = response.body()?.rates
                    val rate = rates?.get(to)
                    if (rate != null) {
                        _exchangeRate.value = amount * rate
                    }
                }
            }

            override fun onFailure(call: Call<ExchangeRatesResponse>, t: Throwable) {
                _exchangeRate.value = null
            }
        })
    }
}