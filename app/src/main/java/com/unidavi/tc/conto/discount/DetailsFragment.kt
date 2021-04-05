package com.unidavi.tc.conto.discount

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.unidavi.tc.conto.R
import com.unidavi.tc.conto.database.ContoDataBase
import com.unidavi.tc.conto.databinding.FragmentDetailsBinding
import com.unidavi.tc.conto.viewModels.DiscountDetailsViewModel
import com.unidavi.tc.conto.viewModels.DiscountDetailsViewModelFactory
import com.unidavi.tc.conto.viewModels.DiscountViewModel
import com.unidavi.tc.conto.viewModels.DiscountViewModelFactory
import kotlinx.android.synthetic.main.activity_discount.*
import timber.log.Timber

class DetailsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                savedInstanceState: Bundle?): View? {

        val binding: FragmentDetailsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_details, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = ContoDataBase.getInstance(application)
        val discountKey = activity?.intent?.extras?.getLong("discountKey")
        val viewModelFactory =
            discountKey?.let {
                DiscountDetailsViewModelFactory(
                    it,
                    dataSource
                )
            }
        val discountDetailViewModel = viewModelFactory?.let {
            ViewModelProvider(
                this,
                it
            ).get(DiscountDetailsViewModel::class.java)
        }
        discountDetailViewModel?.inactivateDiscount?.observe(viewLifecycleOwner, {
            if(it == true){
                Snackbar.make(
                    requireView().findViewById(R.id.text_details_code),
                    "Desconto utilizado",
                    Snackbar.LENGTH_LONG
                ).show()
                discountDetailViewModel.doneInactivatingDiscount()

            }
        })
        binding.discountDetailViewModel = discountDetailViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

}