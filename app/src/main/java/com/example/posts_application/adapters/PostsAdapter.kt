package com.example.posts_application.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.posts_application.data_classes.PostsListItem
import com.example.posts_application.databinding.PostBinding

class PostsAdapter() : RecyclerView.Adapter<PostsAdapter.HomeViewHolder>() {

    private var postsList = emptyList<PostsListItem>()
    lateinit var onItemClick :((PostsListItem) -> Unit)


    fun setPosts(postsList: List<PostsListItem>) {
        this.postsList = postsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(PostBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val currentItem = postsList[position]
        Glide.with(holder.itemView).load(currentItem.post_image).into(holder.binding.postImg)
        holder.binding.postTittle.text = currentItem.post_title

        holder.itemView.setOnClickListener{
            onItemClick.invoke(postsList[position])
        }

    }

    class HomeViewHolder(val binding: PostBinding) : RecyclerView.ViewHolder(binding.root)
}
