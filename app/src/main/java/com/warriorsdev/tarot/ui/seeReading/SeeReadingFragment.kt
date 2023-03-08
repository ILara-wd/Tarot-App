package com.warriorsdev.tarot.ui.seeReading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.warriorsdev.tarot.tools.TarotUtils.ARGS_ITEM_CARD
import com.warriorsdev.tarot.databinding.FragmentSeeReadingBinding
import com.warriorsdev.tarot.tools.Preferences
import com.warriorsdev.tarot.ui.seeReading.adapter.DescriptionCardAdapter
import com.warriorsdev.tarot.ui.theWay.model.TarotCards
import com.warriorsdev.tarot.ui.theWay.model.ItemCard

@Suppress("DEPRECATION")
class SeeReadingFragment : Fragment() {

    private var _binding: FragmentSeeReadingBinding? = null
    private val binding get() = _binding!!
    private val cardDescription = ArrayList<ItemCard>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeeReadingBinding.inflate(inflater, container, false)
        binding.rvCardsDescription.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvCardsDescription.adapter = DescriptionCardAdapter(cardDescription)
        return binding.root
    }

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        val bundle = this.arguments
        if (bundle != null) {
            val model = bundle.getParcelable<TarotCards>(ARGS_ITEM_CARD)
            cardDescription.addAll(model!!.cards)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
