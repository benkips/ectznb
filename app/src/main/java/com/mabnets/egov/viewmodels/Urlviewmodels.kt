package com.mabnets.egov.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mabnets.egov.Repo.Repostuff
import com.mabnets.egov.models.Mydata
import com.mabnets.egov.Repo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Urlviewmodels @Inject constructor(private val repostuff: Repostuff) :
    ViewModel() {
    private val _urlzResponse: MutableLiveData<Resource<List<Mydata>>> = MutableLiveData()
    val urlzResponse: LiveData<Resource<List<Mydata>>>
        get() = _urlzResponse

    fun getulrs() = viewModelScope.launch {
        _urlzResponse.value = Resource.Loading
        _urlzResponse.value=repostuff.geturlz()
    }
}