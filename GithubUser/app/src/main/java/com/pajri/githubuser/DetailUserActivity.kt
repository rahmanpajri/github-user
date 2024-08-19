package com.pajri.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.pajri.githubuser.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailUserViewModel: DetailUserViewModel

    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
        const val USERS_ID = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var username = intent.getStringExtra(USERS_ID)
        supportActionBar?.title = username
        detailUserViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailUserViewModel::class.java]

        if(username != null){
            detailUserViewModel.getDetailUsers(username)
        }

        detailUserViewModel.listDetailUser.observe(this){username->
            setDetailUser(username)
        }

        detailUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, username.toString())
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager){tab, position ->
            if (position == 1) {
                tab.text = resources.getString(TAB_TITLES[position])
            } else {
                tab.text = resources.getString(TAB_TITLES[position])
            }
        }.attach()

    }

    private fun setDetailUser(username: DetailUserResponse?) {
        Glide.with(this@DetailUserActivity)
            .load(username?.avatarUrl)
            .into(binding.userProfile)
        binding.name.text = username?.name
        binding.username.text = "@${username?.login}"
        binding.followers.text = "${username?.followers} Followers"
        binding.following.text = "${username?.following} Following"

    }


    private fun showLoading(isLoading: Boolean){
        if(isLoading){
            binding.progressLoading.visibility = View.VISIBLE
        }else{
            binding.progressLoading.visibility = View.GONE
        }
    }
}