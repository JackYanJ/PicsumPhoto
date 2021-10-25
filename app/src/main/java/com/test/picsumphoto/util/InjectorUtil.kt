package com.test.picsumphoto.util

import com.test.picsumphoto.data.MainRepository
import com.test.picsumphoto.data.network.PicsumPohotoNetwork
import com.test.picsumphoto.ui.community.PhotosViewModelFactory


/**
 * @ClassName InjectorUtil
 * @Description logic control class
 * @Author mailo
 * @Date 2021/10/25
 */
object InjectorUtil {
    private fun getLaunchesRepository() =
        MainRepository.getInstance(PicsumPohotoNetwork.getInstance())

    fun getPhotosViewModelFactory() = PhotosViewModelFactory(getLaunchesRepository())
}