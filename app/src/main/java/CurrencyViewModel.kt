import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CurrencyViewModel(private val repository: CurrencyRepository) : ViewModel() {
    private val _exchangeRate = MutableLiveData<Double>()
    val exchangeRate: LiveData<Double> get() = _exchangeRate

    fun convertCurrency(amount: Double, from: String, to: String) {
        Log.d("CurrencyConverter", "Chamando API para converter: $amount de $from para $to")

        repository.getExchangeRates(from, to) { exchangeRate ->
            if (exchangeRate != null) {
                val result = amount * exchangeRate
                _exchangeRate.postValue(result) // Atualiza o LiveData com o valor convertido
                Log.d("CurrencyConverter", "Conversão bem-sucedida: $amount * $exchangeRate = $result")
            } else {
                Log.e("CurrencyConverter", "Erro ao obter taxa de câmbio")
            }
        }
    }
}