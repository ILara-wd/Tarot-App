package com.warriorsdev.tarot.ui.theWay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.warriorsdev.tarot.R
import com.warriorsdev.tarot.databinding.FragmentTheWayBinding
import com.warriorsdev.tarot.tools.CardDialogFragment
import com.warriorsdev.tarot.tools.Preferences
import com.warriorsdev.tarot.tools.TarotUtils
import com.warriorsdev.tarot.tools.TarotUtils.ARGS_ITEM_CARD
import com.warriorsdev.tarot.ui.theWay.adapter.MazoCardAdapter
import com.warriorsdev.tarot.ui.theWay.model.CardPosition
import com.warriorsdev.tarot.ui.theWay.model.ItemCard
import com.warriorsdev.tarot.ui.theWay.model.TarotCards
import com.warriorsdev.tarot.ui.theWay.viewModel.TheWayViewModel

class TheWayFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentTheWayBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterMissing: MazoCardAdapter
    private var cardsTarot: ArrayList<ItemCard> = ArrayList()
    private lateinit var theWayViewModel: TheWayViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        theWayViewModel = ViewModelProvider(this)[TheWayViewModel::class.java]
        _binding = FragmentTheWayBinding.inflate(inflater, container, false)
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
        binding.ivThree.setOnClickListener(this)
        binding.ivThree.contentDescription = getString(R.string.content_description)
        binding.ivFour.setOnClickListener(this)
        binding.ivFour.contentDescription = getString(R.string.content_description)
        binding.ivFive.setOnClickListener(this)
        binding.ivFive.contentDescription = getString(R.string.content_description)
        binding.btnViewReading.setOnClickListener {
            Preferences(requireContext()).updateReading()
            val bundle = Bundle()
            bundle.putParcelable(ARGS_ITEM_CARD, TarotCards(cardsTarot))
            findNavController().navigate(R.id.action_nav_the_way_to_nav_tarot_description, bundle)
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
        binding.tvPutHereBottom.text = getString(R.string.text_empty)
        binding.tvPutHereTop.text = getString(R.string.text_empty)
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
            CardPosition.TOP -> {
                binding.tvPutHereTop.text = getString(R.string.text_put_here)
            }
            CardPosition.BOTTOM -> {
                binding.tvPutHereBottom.text = getString(R.string.text_put_here)
            }
            CardPosition.CENTER -> {
                binding.tvPutHereCenter.text = getString(R.string.text_put_here)
            }
        }
    }

    private fun showCardInImageView(it: ItemCard) {
        var isRemoveCard = true
        if (binding.ivOne.contentDescription == getString(R.string.content_description)) {
            binding.ivOne.setImageResource(TarotUtils.getResourceByName(it.image))
            binding.ivOne.contentDescription = it.title
            binding.tvTitleLeft.text = it.title
            showPutHere(CardPosition.RIGHT)
        } else if (binding.ivTwo.contentDescription == getString(R.string.content_description)) {
            binding.ivTwo.setImageResource(TarotUtils.getResourceByName(it.image))
            binding.ivTwo.contentDescription = it.title
            binding.tvTitleRight.text = it.title
            showPutHere(CardPosition.TOP)
        } else if (binding.ivThree.contentDescription == getString(R.string.content_description)) {
            binding.ivThree.setImageResource(TarotUtils.getResourceByName(it.image))
            binding.ivThree.contentDescription = it.title
            binding.tvTitleTop.text = it.title
            showPutHere(CardPosition.BOTTOM)
        } else if (binding.ivFour.contentDescription == getString(R.string.content_description)) {
            binding.ivFour.setImageResource(TarotUtils.getResourceByName(it.image))
            binding.ivFour.contentDescription = it.title
            binding.tvTitleCardBottom.text = it.title
            showPutHere(CardPosition.CENTER)
        } else if (binding.ivFive.contentDescription == getString(R.string.content_description)) {
            binding.ivFive.setImageResource(TarotUtils.getResourceByName(it.image))
            binding.ivFive.contentDescription = it.title
            binding.tvTitleCenter.text = it.title
            showPutHere(CardPosition.TOP)
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
