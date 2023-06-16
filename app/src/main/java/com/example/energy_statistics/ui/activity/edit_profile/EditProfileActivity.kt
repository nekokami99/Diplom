package com.example.energy_statistics.ui.activity.edit_profile

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.energy_statistics.R
import com.example.energy_statistics.Utils
import com.example.energy_statistics.data.Repository
import com.example.energy_statistics.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private val viewmodel: EditProfileViewModel by lazy {
        ViewModelProvider(this)[EditProfileViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
        binding.apply {
            lifecycleOwner = this@EditProfileActivity
            editProViewmodel = viewmodel
        }
        setupView()
        setupObserve()
    }

    private fun setupObserve() {
        viewmodel.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                val contentResolver = contentResolver
                try {
                    val bitmap = if (Build.VERSION.SDK_INT < 28) {
                        MediaStore.Images.Media.getBitmap(contentResolver, uri)
                    } else {
                        val source = ImageDecoder.createSource(contentResolver, uri)
                        ImageDecoder.decodeBitmap(source)
                    }
                    val bitmapResize = Bitmap.createScaledBitmap(bitmap, 300, 400, true);
                    binding.imgAvatar.setImageBitmap(bitmapResize)
                    val strAvatar = Utils.convertBitmaptoBase64(bitmapResize)
                    viewmodel.setAvatar(strAvatar)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    private fun setupView() {
        binding.imgBack.setOnClickListener {
            onBackPressed()
        }
        binding.imgAvatar.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        val base64Avatar = Repository.instance.userInfo?.avatar
        if (!base64Avatar.isNullOrBlank()) {
            val bitmap = Utils.convertBase64toBitmap(base64Avatar)
            binding.imgAvatar.setImageBitmap(bitmap)
        }
    }

}