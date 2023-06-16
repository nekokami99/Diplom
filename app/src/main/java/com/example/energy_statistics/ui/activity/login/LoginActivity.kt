package com.example.energy_statistics.ui.activity.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.energy_statistics.R
import com.example.energy_statistics.data.Repository
import com.example.energy_statistics.databinding.ActivityLoginBinding
import com.example.energy_statistics.ui.activity.home.HomeActivity
import com.example.energy_statistics.ui.activity.signup.SignUpActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.apply {
            lifecycleOwner = this@LoginActivity
            loginViewModel = viewModel
        }
        isLoginFirstTime()
        startSignUp()
    }

    private fun startSignUp() {
        viewModel.startSignup.observe(this) {
            if (it) {
                val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun isLoginFirstTime() {
        if (!Repository.instance.accessToken.isNullOrBlank()) {
            Log.e("TOKEN__", Repository.instance.accessToken)
            goToHomePage()
        } else {
            callApiLogin()
        }
    }

    private fun callApiLogin(){
        viewModel.success.observe(this){
            if(!it.isNullOrBlank()) {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                Repository.instance.accessToken?.let { it1 -> Log.e("TOKEN__-", it1) }
                goToHomePage()
            }
        }
        viewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }

    private fun goToHomePage() {
        val intent = Intent(this, HomeActivity::class.java)
        runBlocking {
            launch {
                delay(500)
                startActivity(intent)
                finish()
            }
        }

    }
}