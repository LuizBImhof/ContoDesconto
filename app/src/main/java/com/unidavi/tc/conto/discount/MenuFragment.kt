package com.unidavi.tc.conto.discount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.unidavi.tc.conto.R
import com.unidavi.tc.conto.adapters.ProductAdapter
import com.unidavi.tc.conto.database.ContoDataBase
import com.unidavi.tc.conto.databinding.FragmentMenuBinding
import com.unidavi.tc.conto.viewModels.ProductViewModel
import com.unidavi.tc.conto.viewModels.ProductViewModelFactory
import timber.log.Timber


class MenuFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentMenuBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_menu, container, false
        )

        val application = requireNotNull(this.activity).application

        val dataSource = ContoDataBase.getInstance(application)

        val establishmentKey = activity?.intent?.extras?.getLong("establishmentKey")

        Timber.i("Id do desconto: %s", establishmentKey.toString())

        val viewModelFactory = establishmentKey?.let {
            ProductViewModelFactory(
                dataSource, application,
                it
            )
        }

        val productViewModel = viewModelFactory?.let {
            ViewModelProvider(
                this,
                it
            ).get(ProductViewModel::class.java)
        }

        val adapter = ProductAdapter(
        )
        productViewModel?.products?.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.menuList.adapter = adapter

        binding.lifecycleOwner = this
        binding.productViewModel = productViewModel


        return binding.root
    }


}