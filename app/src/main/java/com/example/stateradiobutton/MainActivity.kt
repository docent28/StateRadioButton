package com.example.stateradiobutton // Убедитесь, что имя пакета совпадает с вашим

import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// ВАЖНО: импортируйте ваш класс Binding
// Если ваш пакет com.example.stateradiobutton, то импорт будет:
import com.example.stateradiobutton.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null // Делаем nullable для безопасности

    private val KEY_SELECTED_RADIO_ID = "selected_radio_id"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            // Инициализируем binding с обработкой ошибок
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding?.root)

            if (binding == null) {
                Toast.makeText(this, "Ошибка инициализации интерфейса", Toast.LENGTH_SHORT).show()
                return
            }

            // Настраиваем слушатель для RadioGroup
            binding?.radioGroupLanguages?.setOnCheckedChangeListener { group, checkedId ->
                updateResultText(checkedId)
            }

            // Восстанавливаем состояние
            if (savedInstanceState != null) {
                val savedRadioId = savedInstanceState.getInt(KEY_SELECTED_RADIO_ID, -1)
                if (savedRadioId != -1) {
                    binding?.radioGroupLanguages?.check(savedRadioId)
                    updateResultText(savedRadioId)
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Ошибка: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        try {
            val selectedId = binding?.radioGroupLanguages?.checkedRadioButtonId ?: -1
            if (selectedId != -1) {
                outState.putInt(KEY_SELECTED_RADIO_ID, selectedId)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun updateResultText(checkedId: Int) {
        try {
            val resultText = if (checkedId != -1) {
                val radioButton = findViewById<RadioButton>(checkedId)
                "Выбран: ${radioButton.text}"
            } else {
                "(ничего не выбрано)"
            }
            binding?.textResult?.text = resultText
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null // Освобождаем binding
    }
}