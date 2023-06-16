package com.example.energy_statistics.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.energy_statistics.Utils
import com.example.energy_statistics.data.Repository
import com.example.energy_statistics.databinding.FragmentAccountBinding
import com.example.energy_statistics.ui.activity.edit_profile.EditProfileActivity
import com.example.energy_statistics.ui.activity.home.HomeViewModel
import com.example.energy_statistics.ui.activity.login.LoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    private val viewmodel: HomeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObserve()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupObserve() {
        viewmodel.userInfo.observe(this) {
            setUserInfo()
        }
    }

    private fun setUserInfo() {
        val user = Repository.instance.userInfo
        if (user != null) {
            binding.txtUserName.text = "Name: ${user.name}"
            binding.txtMail.text = "Email: ${user.email}"
            binding.txtAge.text = "Age: ${user.age}"
            binding.txtPhoneNumber.text = "Phone number: ${user.phoneNumber}"
            binding.txtAddress.text = "Address: ${user.address}"
            val bitmap = user.avatar?.let { Utils.convertBase64toBitmap(it) }
            binding.imgAvatar.setImageBitmap(bitmap)
        } else {
            viewmodel.getUserInfo()
        }
    }

    private fun setupView() {
        binding.btnChangeProfile.setOnClickListener {
            val intent = Intent(context, EditProfileActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogout.setOnClickListener {
            Repository.instance.shared.logout{
                runBlocking {
                    launch {
                        delay(500)
                        val intent = Intent(context, LoginActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
        setUserInfo()
    }

    override fun onResume() {
        super.onResume()
        setUserInfo()
    }

}