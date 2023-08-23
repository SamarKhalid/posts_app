package com.example.posts_application.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.posts_application.R
import com.example.posts_application.data_classes.PostsListItem
import com.example.posts_application.databinding.ActivityMainBinding
import com.example.posts_application.databinding.ActivityPostsBinding
import com.example.posts_application.viewmodel.HomeViewModel

class PostsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostsBinding
    private lateinit var postTittle: String
    private lateinit var postImage: String
    private lateinit var postMessage: String
    private lateinit var postId: String
    private lateinit var homeMvvm: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getPostDetails()
        setPostDetails()

    }

    private fun getPostDetails() {
        val intent = intent
        postTittle = intent.getStringExtra(MainActivity.POST_TITTLE)!!
        postImage = intent.getStringExtra(MainActivity.POST_IMAGE)!!
        postMessage = intent.getStringExtra(MainActivity.POST_MESSAGE)!!
    }

    private fun setPostDetails() {
        binding.postTittle.text = postTittle
        Glide.with(applicationContext)
            .load(postImage).into(binding.postImg)
        binding.postMessage.text = postMessage
    }

}