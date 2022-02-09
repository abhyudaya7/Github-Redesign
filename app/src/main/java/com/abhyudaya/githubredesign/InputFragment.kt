package com.abhyudaya.githubredesign

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.abhyudaya.githubredesign.databinding.FragmentInputBinding


class InputFragment : Fragment() {

    var _binding: FragmentInputBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentInputBinding.inflate(inflater, container, false)
        val view = binding.root

        val action = InputFragmentDirections.actionInputFragmentToProfileFragment(binding.username.text.toString())
        view.findNavController().navigate(action)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}