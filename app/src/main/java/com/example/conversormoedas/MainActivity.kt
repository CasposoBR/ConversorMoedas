package com.example.conversormoedas

import CurrencyRepository
import CurrencyViewModel
import CurrencyViewModelFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CurrencyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializando o ViewModel
        val repository = CurrencyRepository()
        viewModel = ViewModelProvider(this,
            CurrencyViewModelFactory(repository)
        )[CurrencyViewModel::class.java]

        // Referências para os componentes de UI
        val editAmount = findViewById<EditText>(R.id.editAmount)
        val txtLeft = findViewById<TextView>(R.id.txtLeft)
        val txtRight = findViewById<TextView>(R.id.txtRight)
        val btnSwap = findViewById<ImageButton>(R.id.btnSwap)
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
            Log.d("CurrencyConverter", "Botão de conversão clicado!")

            val amount = editAmount.text.toString().toDoubleOrNull()
            if (amount != null) {
                Log.d("CurrencyConverter", "Converting: Amount = $amount, From = ${txtLeft.text}, To = ${txtRight.text}")
                viewModel.convertCurrency(amount, txtLeft.text.toString(), txtRight.text.toString())
            } else {
                txtResult.text = "Por favor, insira um valor válido"
                Log.e("CurrencyConverter", "Valor inserido inválido")
            }
        }

        // Observador para atualizar o resultado quando a taxa de câmbio for obtida
        viewModel.exchangeRate.observe(this) { result ->
            Log.d("CurrencyConverter", "Taxa de câmbio recebida: $result")
            txtResult.text = "Resultado: $result"
        }


        }
    }
