package com.unidavi.tc.conto.main

import android.content.Intent
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
import com.unidavi.tc.conto.database.TYPE_ENTERTAINMENT
import com.unidavi.tc.conto.databinding.FragmentEntertainmentTypeBinding
import com.unidavi.tc.conto.discount.DiscountActivity
import com.unidavi.tc.conto.viewModels.DiscountViewModel
import com.unidavi.tc.conto.viewModels.DiscountViewModelFactory


class EntertainmentTypeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentEntertainmentTypeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_entertainment_type, container, false
        )
        val application = requireNotNull(this.activity).application
        val dataSource = ContoDataBase.getInstance(application)

        val viewModelFactory =
            DiscountViewModelFactory(dataSource, application, TYPE_ENTERTAINMENT, 0)

        val discountViewModel =
            ViewModelProvider(this, viewModelFactory).get(DiscountViewModel::class.java)

        val adapter = DiscountAdapter(DiscountListener { discountId ->
            discountViewModel.onDiscountClicked(discountId)
        })

        binding.entertainmentProductOfferList.adapter = adapter

        discountViewModel.discounts?.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })

        discountViewModel.navigateToDiscountDetail.observe(viewLifecycleOwner, { discount ->
            discount?.let {
                val intent = Intent(this.context, DiscountActivity::class.java)
                intent.putExtra("discountKey", discount.discountId)
                intent.putExtra("establishmentName", discount.establishment.name)
                intent.putExtra("establishmentKey", discount.establishment.establishmentId)
                startActivity(intent)
                discountViewModel.onDiscountDetailNavigated()
            }
        })
        return binding.root
    }

}