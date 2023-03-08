package com.warriorsdev.tarot.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.warriorsdev.tarot.R
import com.warriorsdev.tarot.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.cardOneCard.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_nav_one_card)
        }
        binding.cardClassic.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_nav_classic)
        }
        binding.cardTheWay.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_nav_the_way)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
