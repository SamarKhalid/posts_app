package com.example.posts_application.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.posts_application.data_classes.PostsListItem
import com.example.posts_application.retrofit.RetrofitInstance
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private var postsLiveData = MutableLiveData<List<PostsListItem>>()


    fun getAllPosts() {
        RetrofitInstance.api.getAllPosts().enqueue(object : Callback<List<PostsListItem>> {
            override fun onResponse(call: Call<List<PostsListItem>>, response: Response<List<PostsListItem>>) {
                if (response.isSuccessful) {
                    postsLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<PostsListItem>>, t: Throwable) {
                Log.d("Posts", t.message.toString())
            }
        })
    }

    fun createPost(
        title: String,
        message: String,
        imagePart: MultipartBody.Part,
        onResponse: (Boolean) -> Unit
    ) {
        val titleRequestBody = title.toRequestBody(MultipartBody.FORM)
        val messageRequestBody = message.toRequestBody(MultipartBody.FORM)

        RetrofitInstance.api.createPost(titleRequestBody, messageRequestBody, imagePart)
            .enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    onResponse(response.isSuccessful && response.body() == "Done")
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    onResponse(false)
                    Log.d("create", t.message.toString())

                }
            })
    }
    fun deletePost(post: String, onResponse: (Boolean) -> Unit) {
        RetrofitInstance.api.deletePost(post).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                onResponse(response.isSuccessful && response.body() == "Done")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                onResponse(false)
                Log.d("Delete", t.message.toString())
            }
        })
    }




    fun observerPostsLiveData(): LiveData<List<PostsListItem>> {
        return postsLiveData
    }
}
