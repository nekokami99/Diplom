package com.example.energy_statistics.ui.activity.signup

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.energy_statistics.R
import com.example.energy_statistics.databinding.ActivitySignUpBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewmodel: SignUpViewModel by lazy {
        ViewModelProvider(owner = this)[SignUpViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        binding.apply {
            lifecycleOwner = this@SignUpActivity
            signupViewModel = viewmodel
        }

        setupView()
        setupObserve()
    }

    private fun setupObserve() {
        viewmodel.success.observe(this) {
            if (it) {
                runBlocking {
                    launch {
                        delay(1500)
                        onBackPressed()
                    }
                }

            }
        }
        viewmodel.showProgress.observe(this) {
            if (it) {
                binding.lnProgress.visibility = View.VISIBLE
            } else {
                binding.lnProgress.visibility = View.GONE
            }
        }

        viewmodel.message.observe(this) { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun setupView() {
        binding.imgBack.setOnClickListener {
            onBackPressed()
        }
        binding.lnProgress.visibility = View.GONE
    }
}