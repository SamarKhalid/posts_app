package com.example.posts_application.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.posts_application.databinding.ActivityMainBinding

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.posts_application.adapters.PostsAdapter
import com.example.posts_application.data_classes.PostsListItem
import com.example.posts_application.viewmodel.HomeViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var postsAdapter: PostsAdapter

    companion object{
        const val POST_ID = "com.example.posts_application.activities.postId"
        const val POST_TITTLE = "com.example.posts_application.activities.postTittle"
        const val POST_IMAGE = "com.example.posts_application.activities.postImage"
        const val POST_MESSAGE = "com.example.posts_application.activities.postMessage"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]

        postsAdapter = PostsAdapter()
        preparePostsRecyclerView()
        homeMvvm.getAllPosts()
        observerPosts()
        onPostClick()

        binding.addPostBtn.setOnClickListener {
            val intent = Intent(this , CreatePostActivity::class.java )
            startActivity(intent)
        }
    }

    private fun preparePostsRecyclerView(){
        binding.postsRv.apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            adapter = postsAdapter

        }
    }

    private fun observerPosts() {
        homeMvvm.observerPostsLiveData().observe(this) { postsList ->
            postsAdapter.setPosts(postsList)
        }
    }

    private fun onPostClick() {
        postsAdapter.onItemClick= {
            val intent = Intent(this , PostsActivity::class.java )
            intent.putExtra(POST_ID, it.id)
            intent.putExtra(POST_TITTLE, it.post_title)
            intent.putExtra(POST_IMAGE, it.post_image)
            intent.putExtra(POST_MESSAGE, it.post_message)
            startActivity(intent)
        }
    }
}