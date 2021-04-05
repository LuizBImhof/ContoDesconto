package com.unidavi.tc.conto.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.unidavi.tc.conto.R
import com.unidavi.tc.conto.adapters.DiscountAdapter
import com.unidavi.tc.conto.adapters.DiscountListener
import com.unidavi.tc.conto.database.ContoDataBase
import com.unidavi.tc.conto.databinding.FragmentUsedOffersBinding
import com.unidavi.tc.conto.viewModels.DiscountViewModel
import com.unidavi.tc.conto.viewModels.DiscountViewModelFactory


class UsedOffersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentUsedOffersBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_used_offers, container, false
        )

        val application = requireNotNull(this.activity).application

        val dataSource = ContoDataBase.getInstance(application)

        val viewModelFactory = DiscountViewModelFactory(dataSource, application, -3, 0)

        val discountViewModel =
            ViewModelProvider(this, viewModelFactory).get(DiscountViewModel::class.java)

        val adapter = DiscountAdapter(DiscountListener { 
        })

        binding.usedOffersList.adapter = adapter

        discountViewModel.discounts?.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })


        discountViewModel.navigateToDiscountDetail.observe(viewLifecycleOwner, { discount ->
            discount?.let {

            }
        })


        return binding.root
    }

}