package com.mabnets.egov.Repo
import com.mabnets.egov.models.Mydata
import retrofit2.http.*

interface ApiInterface {
    companion object{
        const val BASE_URL="http://ecitizen.howtoinkenya.co.ke/admin/"
    }

    //Getting urls
    @GET("allservices")
    suspend  fun geturls(): List<Mydata>

}