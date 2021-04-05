package com.unidavi.tc.conto.discount

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NO_HISTORY
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.unidavi.tc.conto.R
import com.unidavi.tc.conto.adapters.DiscountAdapter
import com.unidavi.tc.conto.adapters.DiscountListener
import com.unidavi.tc.conto.database.ContoDataBase
import com.unidavi.tc.conto.databinding.FragmentMoreDiscountsBinding
import com.unidavi.tc.conto.viewModels.DiscountViewModel
import com.unidavi.tc.conto.viewModels.DiscountViewModelFactory
import timber.log.Timber


class MoreDiscountsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val discountId = activity?.intent?.extras?.getLong("discountKey")

        Timber.i("""Id do desconto $discountId""")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentMoreDiscountsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_more_discounts, container, false
        )

        val application = requireNotNull(this.activity).application

        val dataSource = ContoDataBase.getInstance(application)

        val discountKey = activity?.intent?.extras?.getLong("discountKey")

        val viewModelFactory =
            discountKey?.let {
                DiscountViewModelFactory(
                    dataSource,
                    application,
                    -1,
                    it
                )
            }


        val discountViewModel = viewModelFactory?.let {
            ViewModelProvider(
                this,
                it
            ).get(DiscountViewModel::class.java)
        }

        val adapter = DiscountAdapter(DiscountListener { discountId ->
            discountViewModel?.onDiscountClicked(discountId)
        })

        binding.moreDiscountsList.adapter = adapter

        if (discountViewModel != null) {
            discountViewModel.discounts?.observe(viewLifecycleOwner, Observer {
                it?.let {
                    adapter.submitList(it)
                }
            })
        }

        discountViewModel?.navigateToDiscountDetail?.observe(
            viewLifecycleOwner,
            { discount ->
                discount?.let {
                    val intent = Intent(this.context, DiscountActivity::class.java)
                    intent.putExtra("discountKey", discount.discountId)
                    intent.putExtra("establishmentName", discount.establishment.name)
                    intent.putExtra("establishmentKey", discount.establishment.establishmentId)

                    startActivity(intent)
                }
            })
        return binding.root
    }


}