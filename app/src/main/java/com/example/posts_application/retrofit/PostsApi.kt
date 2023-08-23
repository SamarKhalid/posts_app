package com.example.posts_application.retrofit

import com.example.posts_application.data_classes.PostsListItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PostsApi {

    @GET("getposts")
    fun getAllPosts(): Call<List<PostsListItem>>

    @Multipart
    @POST("create")
    fun createPost(
        @Part("post_title") title: RequestBody,
        @Part("post_message") message: RequestBody,
        @Part image: MultipartBody.Part,
    ): Call<String>

    @POST("deletepost")
    fun deletePost(@Body postId: String): Call<String>
}