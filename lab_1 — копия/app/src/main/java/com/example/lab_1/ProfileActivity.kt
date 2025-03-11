package com.example.lab_1

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.lab_1.adapters.PostAdapter
import com.example.lab_1.models.Post

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        val avatar: ImageView = findViewById(R.id.avatar)
        Glide.with(this)
            .load("https://i.pinimg.com/736x/fb/63/4f/fb634f547d3661de6436ec0f78e969b6.jpg")
            .transform(CircleCrop())
            .into(avatar)

        val name: TextView = findViewById(R.id.name)
        val username: TextView = findViewById(R.id.username)
        val followers: TextView = findViewById(R.id.followers)
        val following: TextView = findViewById(R.id.following)
        val postsCount: TextView = findViewById(R.id.posts)

        name.text = "Gojo Satoru"
        username.text = "@gojo.zip"
        followers.text = "Подписчики: 100000"
        following.text = "Подписки: 5"

        val postsRecyclerView: RecyclerView = findViewById(R.id.posts_recycler_view)
        postsRecyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = PostAdapter()
        postsRecyclerView.adapter = adapter

        fun loadPosts(): List<Post> {
            return listOf(
                Post(1, "Отдыхая", "https://i.pinimg.com/736x/04/a4/83/04a4839c67419d4a0b83bc9215d5a061.jpg", 10, 5),
                Post(2, "Кайф", "https://i.pinimg.com/736x/fb/be/75/fbbe75b7f3e8d7a4c5a19c4bbff97a29.jpg", 15, 3),
                Post(3, "На пляже", "https://i.pinimg.com/736x/90/9e/37/909e3796d79fa54d366a20b9831f8a4b.jpg", 800, 1)

            )
        }


        val newPosts = loadPosts()
        adapter.submitList(newPosts)

        postsCount.text = "Посты: ${newPosts.size}"

        val btnFollow: Button = findViewById(R.id.btn_follow)

        btnFollow.setOnClickListener {
            if (btnFollow.text == "Подписаться") {
                btnFollow.text = "Отписаться"
                val followersCount = findViewById<TextView>(R.id.followers)
                val currentFollowers = followersCount.text.toString().replace(Regex("[^0-9]"), "").toInt()
                followersCount.text = "Подписчики: ${currentFollowers + 1}"
            } else {
                btnFollow.text = "Подписаться"
                val followersCount = findViewById<TextView>(R.id.followers)
                val currentFollowers = followersCount.text.toString().replace(Regex("[^0-9]"), "").toInt()
                followersCount.text = "Подписчики: ${currentFollowers - 1}"

                // unfollowUser(userId)
            }
        }

        val btnMessage: Button = findViewById(R.id.btn_message)

        btnMessage.setOnClickListener {
            val input = EditText(this)
            input.hint = "Введите сообщение"

            AlertDialog.Builder(this)
                .setTitle("Написать сообщение")
                .setView(input)
                .setPositiveButton("Отправить") { dialog, _ ->
                    val message = input.text.toString()
                    if (message.isNotEmpty()) {
                        // Отправляем сообщение
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