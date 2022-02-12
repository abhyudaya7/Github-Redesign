package com.abhyudaya.githubredesign

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abhyudaya.githubredesign.databinding.FragmentProfileBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


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

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val userName = ProfileFragmentArgs.fromBundle(requireArguments()).userName

        viewModel.name.observe(viewLifecycleOwner, Observer { newVal ->
            binding.name.text = newVal
        })

        viewModel.login.observe(viewLifecycleOwner, Observer { newVal ->
            binding.userName.text = "@$newVal"
        })

        viewModel.bio.observe(viewLifecycleOwner, Observer { newVal ->
            binding.bio.text = newVal
        })

        viewModel.followers.observe(viewLifecycleOwner, Observer { newVal ->
            binding.followersData.text = newVal.toString()
        })

        viewModel.following.observe(viewLifecycleOwner, Observer { newVal ->
            binding.followingData.text = newVal.toString()
        })

        viewModel.avatarUrl.observe(viewLifecycleOwner, Observer { newVal ->
            Picasso.get().load(newVal).into(binding.profileImage)
        })

        viewModel.getProfileData(userName)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}