package com.example.freemarket.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.freemarket.SignUpAndUpdateActivity
import com.example.freemarket.databinding.FragmentSettingBinding
import com.example.freemarket.repository.LocalDB

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.btProfileImageNameUpdateSetting.setOnClickListener(
            View.OnClickListener {
                val intent = Intent(requireActivity(), SignUpAndUpdateActivity::class.java)
                startActivity(intent)
            },
        )
    }

    override fun onStart() {
        super.onStart()
        val LocalDB = LocalDB()
        val getLocalData = LocalDB.getLocalData(requireActivity())!!
        val userImage = getLocalData.getString("profile_image", "")
        val userName = getLocalData.getString("name", "")
        Glide
            .with(requireActivity())
            .load(userImage)
            .apply(RequestOptions().circleCrop())
            .into(binding.imvProfileImageSetting)

        binding.tvProfileNameSetting.setText(userName)
    }
}
