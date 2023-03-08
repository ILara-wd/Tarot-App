package com.warriorsdev.tarot.tools

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.warriorsdev.tarot.databinding.LayoutDialogFragmentCardBinding

const val ARG_TITLE = "ARG_TITLE"
const val ARG_DESCRIPTION = "ARG_DESCRIPTION"
const val ARG_IMAGE = "ARG_IMAGE"

class CardDialogFragment : DialogFragment() {

    private var _binding: LayoutDialogFragmentCardBinding? = null
    private val binding get() = _binding!!
    private var image: String = ""
    private var title: String = ""
    private var description: String = ""

    fun newInstance(
        titleArg: String,
        descriptionArg: String,
        imageArg: String
    ): CardDialogFragment {
        val myFragment = CardDialogFragment()
        val args = Bundle()
        args.putString(ARG_TITLE, titleArg)
        args.putString(ARG_DESCRIPTION, descriptionArg)
        args.putString(ARG_IMAGE, imageArg)
        myFragment.arguments = args
        return myFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LayoutDialogFragmentCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        image = arguments?.getString(ARG_IMAGE, "").orEmpty()
        title = arguments?.getString(ARG_TITLE, "").orEmpty()
        description = arguments?.getString(ARG_DESCRIPTION, "").orEmpty()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.tvTitle.text = title
        binding.tvDescription.text = description
        binding.ivDescription.setImageResource(TarotUtils.getResourceByName(image))
    }

}
