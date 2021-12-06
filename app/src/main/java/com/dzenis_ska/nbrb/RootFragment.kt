package com.dzenis_ska.nbrb

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.dzenis_ska.nbrb.databinding.FragmentRootBinding
import com.dzenis_ska.nbrb.db.MainApp
import com.dzenis_ska.nbrb.db.MainViewModel
import com.dzenis_ska.nbrb.db.TimeManager

class RootFragment : Fragment(R.layout.fragment_root) {

    var binding: FragmentRootBinding? = null

    private val mainViewModel: MainViewModel by activityViewModels{
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRootBinding.bind(view)

        with(binding!!){
            bCource.setOnClickListener { findNavController().navigate(R.id.courseFragment) }
            imbSettings.setOnClickListener { findNavController().navigate(R.id.settingsFragment) }
        }

    }

}