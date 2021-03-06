package com.abhyudaya.githubredesign.user_interface

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.abhyudaya.githubredesign.viewModel.ProfileViewModelFactory
import com.abhyudaya.githubredesign.retrofit.RepositoryImpl
import com.abhyudaya.githubredesign.adapter.ViewPagerAdapter
import com.abhyudaya.githubredesign.viewModel.ProfileViewModel
import com.abhyudaya.githubredesign.databinding.FragmentProfileBinding
import com.abhyudaya.githubredesign.retrofit.RetrofitFactory
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {
    var _binding: FragmentProfileBinding? = null
    val binding get() = _binding!!
    lateinit var userName: String
    lateinit var viewModel: ProfileViewModel
    lateinit var viewModelFactory: ProfileViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        userName = ProfileFragmentArgs.fromBundle(requireArguments()).userName

        val repository = RepositoryImpl(RetrofitFactory.makeRetrofitService())
        viewModelFactory = ProfileViewModelFactory(repository)
        viewModel = ViewModelProvider(activity as AppCompatActivity, viewModelFactory)
            .get(ProfileViewModel::class.java)

        binding.profileViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = activity?.let { fragmentActivity ->
            ViewPagerAdapter(fragmentActivity.supportFragmentManager, lifecycle)
        }
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = "Pinned Repositories"
                1 -> tab.text = "Repositories"
            }
        }.attach()

        viewModel.userNotFound.observe(viewLifecycleOwner, Observer { userNotFound ->
            if (userNotFound) {
                val action = ProfileFragmentDirections.actionProfileFragmentToUserNotFoundFragment()
                view.findNavController().navigate(action)
            }
        })

        viewModel.avatarUrl.observe(viewLifecycleOwner, Observer { imageUrl ->
            Picasso.get().load(imageUrl).into(binding.profileImage)
        })
        binding.fab.setOnClickListener {
            binding.fab.hide()
        }
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getDataFromApi(userName)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}