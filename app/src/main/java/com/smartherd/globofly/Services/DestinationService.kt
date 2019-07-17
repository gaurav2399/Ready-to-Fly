package com.smartherd.globofly.Services

import com.smartherd.globofly.models.Destination
import retrofit2.Call
import retrofit2.http.*
import java.util.logging.Filter
import javax.crypto.spec.DESKeySpec

interface DestinationService {

    //query parameter type user-defined
    @GET("destination")
    fun getDestinationList(
        @QueryMap filter: HashMap<String, String>
    ):Call<List<Destination>>

    @GET("destination/{id}")
    fun getDestination(@Path("id")id:Int):Call<Destination>


    //POST used for add adta into json format
    @POST("destination")
    fun addDestiantion(@Body newDestination: Destination): Call<Destination>

    //PUT used for add data into FromUrlEncoded
    @FormUrlEncoded
    @PUT("destinations/{id}")
    fun updateDestination(@Path("id")id: Int,
                          @Field("city")city: String,
                          @Field("description")desc: String,
                          @Field("country")country: String):Call<Destination>

    //value name would be same with server fields

    @DELETE("destination/{id}")
    fun deleteDestination(@Path("id")id: Int):Call<Unit>
}