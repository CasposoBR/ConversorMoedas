package com.example.currencyconverter

import CurrencyRepository
import CurrencyViewModel
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.conversormoedas.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CurrencyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializando o ViewModel
        val repository = CurrencyRepository()
        viewModel = ViewModelProvider(this, CurrencyViewModelFactory(repository))[CurrencyViewModel::class.java]

        // Referências para os componentes de UI
        val editAmount = findViewById<EditText>(R.id.editAmount)
        val txtLeft = findViewById<TextView>(R.id.txtLeft)
        val txtRight = findViewById<TextView>(R.id.txtRight)
        val btnSwap = findViewById<Button>(R.id.btnSwap)
        val btnConvert = findViewById<Button>(R.id.btnConvert)
        val txtResult = findViewById<TextView>(R.id.txtResult)

        // Função para trocar valores de text views
        btnSwap.setOnClickListener {
            val temp = txtLeft.text.toString()
            txtLeft.text = txtRight.text.toString()
            txtRight.text = temp
        }

        // Função para converter o valor
        btnConvert.setOnClickListener {
            val amount = editAmount.text.toString().toDoubleOrNull() // Converte para Double
            if (amount != null) {
                // Log para ver o valor e as moedas
                Log.d("CurrencyConverter", "Converting: Amount = $amount, From = ${txtLeft.text}, To = ${txtRight.text}")

                // Aqui chamamos a função do ViewModel para converter a moeda
                viewModel.convertCurrency(amount, txtLeft.text.toString(), txtRight.text.toString())
            } else {
                txtResult.text = "Por favor, insira um valor válido"
            }
        }

        // Observador para atualizar o resultado quando a taxa de câmbio for obtida
        viewModel.exchangeRate.observe(this) { result ->
            if (result != null) {
                txtResult.text = "Resultado: $result ${txtRight.text}"
            } else {
                txtResult.text = "Erro ao obter a taxa de câmbio"
            }
        }
    }
}