package com.abhyudaya.githubredesign.user_interface

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.abhyudaya.githubredesign.adapter.ViewPagerAdapter
import com.abhyudaya.githubredesign.viewModel.ProfileViewModel
import com.abhyudaya.githubredesign.databinding.FragmentProfileBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {
    var _binding: FragmentProfileBinding? = null
    val binding get() = _binding!!

    lateinit var viewModel: ProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(activity as AppCompatActivity).get(ProfileViewModel::class.java)
        val userName = ProfileFragmentArgs.fromBundle(requireArguments()).userName
        viewModel.user = userName
        binding.profileViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.avatarUrl.observe(viewLifecycleOwner, Observer { imageUrl ->
            Picasso.get().load(imageUrl).into(binding.profileImage)
        })

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

        viewModel.getDataFromApi()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}