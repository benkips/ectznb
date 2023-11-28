package com.mabnets.egov.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mabnets.egov.R
import com.mabnets.egov.Util.handleApiError
import com.mabnets.egov.Util.visible
import com.mabnets.egov.adapter.Urladapter
import com.mabnets.egov.databinding.FragmentEservicesBinding
import com.mabnets.egov.viewmodels.Urlviewmodels
import com.mabnets.egov.Repo.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Eservices : Fragment(R.layout.fragment_eservices) {
    private  var _binding : FragmentEservicesBinding?=null
    private val binding get() = _binding!!
    private  val viewmodel by viewModels<Urlviewmodels>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding=FragmentEservicesBinding.bind(view)

        binding.pgbar.visible(false)

        viewmodel.getulrs()
        viewmodel.urlzResponse.observe(viewLifecycleOwner, Observer {
            binding.pgbar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    binding.rvteachings.also { rv ->
                        rv.layoutManager = LinearLayoutManager(requireContext())
                        rv.setHasFixedSize(true)
                        rv.adapter=Urladapter(it.value)
                    }
                }
                is Resource.Failure -> handleApiError(it) { fetchingurls() }
                else -> {}
            }

        })
    }
    private fun fetchingurls(){
        viewmodel.getulrs()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}