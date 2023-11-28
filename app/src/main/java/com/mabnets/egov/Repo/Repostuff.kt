package com.mabnets.egov.Repo

import javax.inject.Inject

class Repostuff @Inject constructor(
    private val apiInterface: ApiInterface,
) : Baserepository() {
    //retrive categorys
    suspend fun geturlz() = safeApiCall {
        apiInterface.geturls()
    }
}