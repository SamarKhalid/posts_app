package com.example.posts_application.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.posts_application.databinding.ActivityCreatePostBinding
import com.example.posts_application.viewmodel.HomeViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class CreatePostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreatePostBinding
    private lateinit var homeMvvm: HomeViewModel

    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                selectedImageUri = uri

            }
        }

    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]

        binding.imgBtn.setOnClickListener {

            imagePickerLauncher.launch("image/*")
        }
        binding.submitBtn.setOnClickListener {
            val title = binding.etTittle.text.toString()
            val message = binding.etMessage.text.toString()

            val selectedImageUri = selectedImageUri

            if (selectedImageUri != null) {
                val imageStream = contentResolver.openInputStream(selectedImageUri)
                val imageFile = File(cacheDir, "temp_image.jpg")

                imageStream?.use { input ->
                    imageFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }

                val imageRequestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                val imagePart = MultipartBody.Part.createFormData("post_image", imageFile.name, imageRequestBody)


                homeMvvm.createPost(title, message, imagePart) { success ->
                    if (success) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.i("Create", "error creating")
                    }
                }
            } else {
                Log.i("Create", "no image selected")
            }
        }
    }
}