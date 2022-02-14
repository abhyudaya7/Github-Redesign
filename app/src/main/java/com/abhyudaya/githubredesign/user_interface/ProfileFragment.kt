package com.abhyudaya.githubredesign.user_interface

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.abhyudaya.githubredesign.viewModel.ProfileViewModel
import com.abhyudaya.githubredesign.databinding.FragmentProfileBinding
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

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val userName = ProfileFragmentArgs.fromBundle(requireArguments()).userName
        viewModel.user = userName
        binding.profileViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.getDataFromApi()
        viewModel.avatarUrl.observe(viewLifecycleOwner, Observer { newVal ->
            Picasso.get().load(newVal).into(binding.profileImage)
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}