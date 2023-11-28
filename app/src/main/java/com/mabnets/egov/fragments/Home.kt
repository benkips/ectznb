package com.mabnets.egov.fragments

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.mabnets.egov.R
import com.mabnets.egov.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Home : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adView: AdView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding =FragmentHomeBinding.bind(view)

        binding.ec.setOnClickListener {v->
            Navigation.findNavController(v).navigate(R.id.action_homescreen_to_eservices)

        }
        binding.bsee.setOnClickListener {v->
            Navigation.findNavController(v).navigate(R.id.action_homescreen_to_eservices)

        }
        binding.breg.setOnClickListener {v->
            val c = "https://accounts.ecitizen.go.ke/register"
            Navigation.findNavController(v).navigate(R.id.action_homescreen_to_wvinfo,
                bundleOf("web" to c)
            )

        }
        /*binding.eb.setOnClickListener {v->
            val c = "https://brs.ecitizen.go.ke/"
            Navigation.findNavController(v).navigate(R.id.action_homescreen_to_wvinfo,bundleOf("web" to c))
        }*/

        adView = AdView(context!!)
        binding.bannerContainertwo.addView(adView)
        adView.adUnitId ="ca-app-pub-4814079884774543/6138421725"

        adView.setAdSize(AdSize.BANNER)
        val adRequest = AdRequest
            .Builder()
            .build()
        // Start loading the ad in the background.
        adView.loadAd(adRequest)

    }
    private val adSize: AdSize
        get() {
            val display =activity?.windowManager!!.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)

            val density = outMetrics.density

            var adWidthPixels = binding.bannerContainertwo.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }

            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context!!, adWidth)
        }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}