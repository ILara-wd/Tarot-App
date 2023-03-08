package com.warriorsdev.tarot.ui.classic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.warriorsdev.tarot.R
import com.warriorsdev.tarot.databinding.FragmentClassicBinding
import com.warriorsdev.tarot.tools.CardDialogFragment
import com.warriorsdev.tarot.tools.TarotUtils
import com.warriorsdev.tarot.tools.TarotUtils.ARGS_ITEM_CARD
import com.warriorsdev.tarot.ui.theWay.adapter.MazoCardAdapter
import com.warriorsdev.tarot.ui.theWay.model.CardPosition
import com.warriorsdev.tarot.ui.theWay.model.ItemCard
import com.warriorsdev.tarot.ui.theWay.model.TarotCards
import com.warriorsdev.tarot.ui.theWay.viewModel.TheWayViewModel

class ClassicFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentClassicBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterMissing: MazoCardAdapter
    private var cardsTarot: ArrayList<ItemCard> = ArrayList()
    private lateinit var theWayViewModel: TheWayViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        theWayViewModel = ViewModelProvider(this)[TheWayViewModel::class.java]
        _binding = FragmentClassicBinding.inflate(inflater, container, false)
        setObserver()
        initView()
        showPutHere(CardPosition.LEFT)
        return binding.root
    }

    private fun setObserver() {
        theWayViewModel.cardsTarot.observe(viewLifecycleOwner) {
            adapterMissing = MazoCardAdapter(it) { tarotCards ->
                cardsTarot.add(tarotCards)
                showCardInImageView(tarotCards)
            }
            binding.rvCards.adapter = adapterMissing
            binding.rvCards.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun initView() {
        cardsTarot.clear()
        binding.ivOne.setOnClickListener(this)
        binding.ivOne.contentDescription = getString(R.string.content_description)
        binding.ivTwo.setOnClickListener(this)
        binding.ivTwo.contentDescription = getString(R.string.content_description)
        binding.ivFive.setOnClickListener(this)
        binding.ivFive.contentDescription = getString(R.string.content_description)
        binding.btnViewReading.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable(ARGS_ITEM_CARD, TarotCards(cardsTarot))
            findNavController().navigate(R.id.action_nav_classic_to_nav_tarot_description, bundle)
        }
    }

    private fun showAlertFragment(itemCard: ItemCard?) {
        if (itemCard != null) {
            val ageSelector = CardDialogFragment().newInstance(
                titleArg = itemCard.title,
                descriptionArg = itemCard.description,
                imageArg = itemCard.image
            )
            ageSelector.show(requireActivity().supportFragmentManager, "card_dialog_fragment")
        }
    }

    private fun clearCard() {
        binding.tvPutHereLeft.text = getString(R.string.text_empty)
        binding.tvPutHereCenter.text = getString(R.string.text_empty)
        binding.tvPutHereRight.text = getString(R.string.text_empty)
    }

    private fun showPutHere(cardPosition: CardPosition) {
        clearCard()
        when (cardPosition) {
            CardPosition.LEFT -> {
                binding.tvPutHereLeft.text = getString(R.string.text_put_here)
            }
            CardPosition.RIGHT -> {
                binding.tvPutHereRight.text = getString(R.string.text_put_here)
            }
            CardPosition.CENTER -> {
                binding.tvPutHereCenter.text = getString(R.string.text_put_here)
            }
            else -> Unit
        }
    }

    private fun showCardInImageView(it: ItemCard) {
        var isRemoveCard = true
        if (binding.ivOne.contentDescription == getString(R.string.content_description)) {
            binding.ivOne.setImageResource(TarotUtils.getResourceByName(it.image))
            binding.ivOne.contentDescription = it.title
            binding.tvTitleLeft.text = it.title
            showPutHere(CardPosition.CENTER)
        } else if (binding.ivFive.contentDescription == getString(R.string.content_description)) {
            binding.ivFive.setImageResource(TarotUtils.getResourceByName(it.image))
            binding.ivFive.contentDescription = it.title
            binding.tvTitleCenter.text = it.title
            showPutHere(CardPosition.RIGHT)
        } else if (binding.ivTwo.contentDescription == getString(R.string.content_description)) {
            binding.ivTwo.setImageResource(TarotUtils.getResourceByName(it.image))
            binding.ivTwo.contentDescription = it.title
            binding.tvTitleRight.text = it.title
            binding.btnViewReading.visibility = View.VISIBLE
            binding.rvCards.visibility = View.INVISIBLE
            binding.tvSelectCard.visibility = View.INVISIBLE
            clearCard()
        } else {
            isRemoveCard = false
        }
        if (isRemoveCard) {
            adapterMissing.removeCard(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        showAlertFragment(theWayViewModel.search(v?.contentDescription.toString()))
    }

}
