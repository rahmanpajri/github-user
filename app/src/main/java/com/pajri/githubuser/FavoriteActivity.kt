package com.pajri.githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pajri.githubuser.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter

    companion object{
        private const val TAG = "FavActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite User"

        adapter = FavoriteAdapter()
        adapter.notifyDataSetChanged()

        val viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User){
                val moveData = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
                moveData.putExtra(DetailUserActivity.USERS_USERNAME, data.login)
                moveData.putExtra(DetailUserActivity.USERS_ID, data.id)
                moveData.putExtra(DetailUserActivity.USERS_AVATAR, data.avatar_url)
                startActivity(moveData)
            }
        })

        binding.apply {
            rvListFavorite.setHasFixedSize(true)
            rvListFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvListFavorite.adapter = adapter
        }
        viewModel.getFavoriteUser()?.observe(this, {
            if(it!=null){
                val list = mapList(it)
                adapter.setList(list)
            }
        })


    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (user in users){
            val userMapped = User(
                user.login,
                user.id,
                user.avatar_url
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }
}