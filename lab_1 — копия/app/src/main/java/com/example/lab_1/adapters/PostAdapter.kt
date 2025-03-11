package com.example.lab_1.adapters

import android.app.AlertDialog
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lab_1.R
import com.example.lab_1.models.Post
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.lab_1.PostDiffCallback

class PostAdapter : ListAdapter<Post, PostAdapter.PostViewHolder>(PostDiffCallback()) {

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val postText: TextView = itemView.findViewById(R.id.post_text)
        val postImage: ImageView = itemView.findViewById(R.id.post_image)
        val likes: TextView = itemView.findViewById(R.id.likes)
        val comments: TextView = itemView.findViewById(R.id.comments)
        val btnLike: Button = itemView.findViewById(R.id.btn_like)
        val btnComment: Button = itemView.findViewById(R.id.btn_comment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.postText.text = post.text
        holder.likes.text = "Лайки: ${post.likes}"
        holder.comments.text = "Комментарии: ${post.comments}"

        if (post.imageUrl != null) {
            Glide.with(holder.itemView.context).load(post.imageUrl).into(holder.postImage)
        } else {
            holder.postImage.visibility = View.GONE
        }

        holder.btnLike.setOnClickListener {
            if (!post.isLikedByAdmin) {
                post.likes += 1
                post.isLikedByAdmin = true

                holder.likes.text = "Лайки: ${post.likes}"
                holder.likes.setTextColor(Color.RED)

                holder.btnLike.setText("❤\uFE0F")
            } else{
                post.likes -= 1
                post.isLikedByAdmin = false

                holder.likes.text = "Лайки: ${post.likes}"
                holder.likes.setTextColor(Color.BLACK)

                holder.btnLike.setText("\uD83E\uDD0D")
            }
        }

        holder.btnComment.setOnClickListener {
            val input = EditText(holder.itemView.context)
            input.hint = "Введите комментарий"

            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Добавить комментарий")
                .setView(input)
                .setPositiveButton("Отправить") { dialog, _ ->
                    val comment = input.text.toString()
                    if (comment.isNotEmpty()) {
                        post.comments += 1
                        holder.comments.text = "Комментарии: ${post.comments}"
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Отмена") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

    }

}