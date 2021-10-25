package com.test.picsumphoto.data.network.api

import com.test.picsumphoto.data.model.Photos
import com.test.picsumphoto.data.network.ServiceCreator
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url


/**
 * @ClassName MainService
 * @Description include all api
 * @Author mailo
 * @Date 2021/10/25
 */
interface MainService {
    /*
    * all photos list
    * */
    @GET("list")
    fun getPhotos(): Call<Photos>


    companion object {

        /**
         * All Launches list
         */
        const val PHOTOS_URL = "${ServiceCreator.BASE_URL}"


    }
}