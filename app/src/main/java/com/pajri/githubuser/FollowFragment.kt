package com.pajri.githubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pajri.githubuser.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    private val listFollower = ArrayList<String>()
    private val listFollowing = ArrayList<String>()
    private lateinit var binding: FragmentFollowBinding
    private lateinit var followViewModel: FollowViewModel
    var username: String? =  null


    companion object{
        const val ARG_POSITION = "section_number"
        const val ARG_USERNAME = "section_username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var position = arguments?.getInt(ARG_POSITION, 0)
        var username = arguments?.getString(ARG_USERNAME)

        followViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(FollowViewModel::class.java)

        followViewModel.username = username.toString()

        followViewModel.isLoading.observe(requireActivity()){loading->
            showLoading(loading)
        }

        if (position == 1){
            followViewModel.getFollower(username.toString())
            followViewModel.listFollowers.observe(viewLifecycleOwner){
                val adapter = ListUserAdapter(it)
                binding.rvFollow.adapter = adapter
                binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())
                adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback{
                    override fun onItemClicked(data: ItemsItem){

                    }
                })
                for(user in it){
                    listFollower.add(
                        """
                            ${user.avatarUrl};${user.login}
                        """.trimIndent()
                    )
                }
            }
        }else{
            followViewModel.getFollowing(username.toString())
            followViewModel.listFollowing.observe(viewLifecycleOwner){
                val adapter = ListUserAdapter(it)
                binding.rvFollow.adapter = adapter
                binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())
                adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback{
                    override fun onItemClicked(data: ItemsItem){
                    }
                })
                for (user in it)(
                        listFollowing.add(
                            """
                                ${user.avatarUrl};${user.login}
                            """.trimIndent()
                        )
                )
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}