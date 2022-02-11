package com.abhyudaya.githubredesign

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        val userName = ProfileFragmentArgs.fromBundle(requireArguments()).userName
        getProfileData(userName)
        return view
    }

    private fun getProfileData(user: String) {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.github.com/users/")
            .build()
            .create(ProfileApiInterface::class.java)

        val retrofitData = retrofitBuilder.getProfileData(user)
        retrofitData.enqueue(object : Callback<ProfileData?> {
            override fun onResponse(call: Call<ProfileData?>, response: Response<ProfileData?>) {
                val responseBody = response.body()!!
                binding.name.text = responseBody.name
                binding.userName.text = responseBody.login
                binding.bio.text = responseBody.bio
                binding.followersData.text = responseBody.followers.toString()
                binding.followingData.text = responseBody.following.toString()
                Picasso.get().load(responseBody.avatar_url).into(binding.profileImage)
            }

            override fun onFailure(call: Call<ProfileData?>, t: Throwable) {
                Log.d("ProfileFragment", "${t.message}")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}