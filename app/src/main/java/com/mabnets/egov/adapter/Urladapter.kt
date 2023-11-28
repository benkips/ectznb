package com.mabnets.egov.adapter

import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.gms.ads.*
import com.mabnets.egov.R
import com.mabnets.egov.databinding.UrlinfBinding
import com.mabnets.egov.models.Mydata

class Urladapter(val urldata:List<Mydata>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val AD_TYPE = 1
    private val DEFAULT_VIEW_TYPE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == AD_TYPE) {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.banner_ad,
                parent,
                false
            )
            return adholderc(view)
        } else {
            val binding = UrlinfBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ContentHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder !is ContentHolder) {
            return
        }
        val itemPosition = position - position / 9
        val currentitem=urldata[itemPosition];
        if(currentitem!=null){
            holder.bind(currentitem)
        }
    }
    override fun getItemCount(): Int {
        var itemCount: Int = urldata.size
        itemCount += itemCount / 9
        return itemCount
    }


    override fun getItemViewType(position: Int): Int {
        return if (position > 1 && position % 9 == 0) {
            AD_TYPE
        } else DEFAULT_VIEW_TYPE
    }

    inner class ContentHolder(val binding:UrlinfBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(urldata: Mydata){
            binding.apply {
                Glide.with(itemView)
                    .load("https://ecitizen.howtoinkenya.co.ke/static/uploads/"+urldata.image)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(ivservice)

                sname.text = urldata.name
                sdescription.text = urldata.description
            }

            binding.cvl.setOnClickListener {
                val x=bundleOf(
                    "web" to urldata.urls,

                )
                Navigation.findNavController(it).navigate(
                    R.id.action_eservices_to_wvinfo,
                    x
                )
            }

        }
    }

    inner class adholderc(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var adView: AdView
        private lateinit var linearbanner:LinearLayout

        init {
            adView = AdView(itemView.context)
            linearbanner = itemView.findViewById(R.id.banner_container)
            linearbanner.addView(adView)
            adView.adUnitId = "ca-app-pub-4814079884774543/6138421725"

            adView.setAdSize(AdSize.BANNER)
            val adRequest = AdRequest
                .Builder()
                .build()
            // Start loading the ad in the background.
            adView.loadAd(adRequest)
        }


    }

}