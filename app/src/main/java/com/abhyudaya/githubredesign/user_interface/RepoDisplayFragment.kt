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
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhyudaya.githubredesign.adapter.RecyclerViewAdapter
import com.abhyudaya.githubredesign.databinding.FragmentRepoDisplayBinding
import com.abhyudaya.githubredesign.viewModel.ProfileViewModel


class RepoDisplayFragment : Fragment() {
    var _binding: FragmentRepoDisplayBinding? = null
    val binding get() = _binding!!

    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentRepoDisplayBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(activity as AppCompatActivity).get(ProfileViewModel::class.java)

        binding.recyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.layoutManager = linearLayoutManager

        viewModel.repoList.observe(viewLifecycleOwner, Observer { repoList ->
            recyclerViewAdapter = activity?.let { fragmentActivity ->
                RecyclerViewAdapter(fragmentActivity.baseContext,repoList)
            }!!
            binding.recyclerView.adapter = recyclerViewAdapter

            recyclerViewAdapter.setOnItemClickListener(object: RecyclerViewAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {
                    val action = ProfileFragmentDirections
                        .actionProfileFragmentToContributorFragment(repoList[position].name, repoList[position].url)
                    view.findNavController().navigate(action)
                }
            })
        })



        return view
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
////        viewModel.user = "greenrobot"
//        binding.recyclerView.setHasFixedSize(true)
//        linearLayoutManager = LinearLayoutManager(this.context)
//        binding.recyclerView.layoutManager = linearLayoutManager
//
//        viewModel.repoList.observe(viewLifecycleOwner, Observer { repoList ->
//            recyclerViewAdapter = activity?.let { fragmentActivity ->
//                RecyclerViewAdapter(fragmentActivity.baseContext,repoList)
//            }!!
//
////            recyclerViewAdapter.notifyDataSetChanged()
//            binding.recyclerView.adapter = recyclerViewAdapter
//        })
//        viewModel.getDataFromApi()
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}