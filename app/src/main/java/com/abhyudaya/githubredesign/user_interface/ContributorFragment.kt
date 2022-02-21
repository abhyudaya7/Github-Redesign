package com.abhyudaya.githubredesign.user_interface

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhyudaya.githubredesign.viewModel.ContributorViewModelFactory
import com.abhyudaya.githubredesign.retrofit.RepositoryImpl
import com.abhyudaya.githubredesign.adapter.ContributorViewAdapter
import com.abhyudaya.githubredesign.viewModel.ContributorViewModel
import com.abhyudaya.githubredesign.databinding.FragmentContributorBinding
import com.abhyudaya.githubredesign.retrofit.RetrofitFactory
import com.abhyudaya.githubredesign.utils.Utils


class ContributorFragment : Fragment() {
    var _binding: FragmentContributorBinding? = null
    val binding get() = _binding!!
    lateinit var viewModel: ContributorViewModel
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var contributorViewAdapter: ContributorViewAdapter
    lateinit var viewModelFactory: ContributorViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentContributorBinding.inflate(inflater, container, false)
        val view = binding.root
        val repoName = ContributorFragmentArgs.fromBundle(requireArguments()).repoName
        val url = ContributorFragmentArgs.fromBundle(requireArguments()).url
        val repository = RepositoryImpl(RetrofitFactory.makeRetrofitService())
        viewModelFactory = ContributorViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[ContributorViewModel::class.java]

        binding.contributorToolbar.title = "Contributors"

        binding.contributorView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this.context)
        binding.contributorView.layoutManager = linearLayoutManager

        viewModel.contributorList.observe(viewLifecycleOwner, Observer { list ->
            contributorViewAdapter = activity?.let { activity ->
                ContributorViewAdapter(activity.baseContext, list)
            }!!
            binding.contributorView.adapter = contributorViewAdapter

            contributorViewAdapter.setOnItemClickListener(object:
                ContributorViewAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {
                    val action = ContributorFragmentDirections
                        .actionContributorFragmentToProfileFragment(list[position].login)
                    view.findNavController().navigate(action)
                }
            })
        })
        val userId = Utils().getUserIdFromUrl(url)
        viewModel.getContributorFromApi(userId, repoName)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}