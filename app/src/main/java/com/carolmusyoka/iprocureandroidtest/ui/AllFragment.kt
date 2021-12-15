package com.carolmusyoka.iprocureandroidtest.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.carolmusyoka.iprocureandroidtest.R
import com.carolmusyoka.iprocureandroidtest.data.db.ProductsDatabase
import com.carolmusyoka.iprocureandroidtest.data.model.Products
import com.carolmusyoka.iprocureandroidtest.data.repository.ProductsRepository
import com.carolmusyoka.iprocureandroidtest.data.viewmodel.ProductViewModel
import com.carolmusyoka.iprocureandroidtest.data.viewmodel.ProductViewModelFactory
import com.carolmusyoka.iprocureandroidtest.databinding.FragmentAllBinding
import com.carolmusyoka.iprocureandroidtest.databinding.FragmentCerealSeedsBinding


class AllFragment : Fragment() {

    private lateinit var _binding: FragmentAllBinding
    private val binding get() = _binding
    private lateinit var productsAdapter: AllProductsAdapter
    private lateinit var productViewModel: ProductViewModel
    private var listOfItems: List<Products> ? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllBinding.inflate(inflater, container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity.let {
            val productsRepository = ProductsRepository(ProductsDatabase(requireContext()))
            val factory = ProductViewModelFactory(productsRepository)
            productViewModel = ViewModelProvider(requireActivity(), factory)
                .get(ProductViewModel::class.java)
        }
        productsAdapter = AllProductsAdapter(listOf())
        binding.products.layoutManager = LinearLayoutManager(context)
        binding.products.adapter = productsAdapter

        productViewModel.allProductItems().observe(viewLifecycleOwner, {list ->
            listOfItems = list
            Log.d("TAG", "onViewCreatedListOfItems: $listOfItems")
            productsAdapter.productItemList = listOfItems!!
            productsAdapter.notifyDataSetChanged()


        })
    }

}