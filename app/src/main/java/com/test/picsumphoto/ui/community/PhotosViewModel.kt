package com.test.picsumphoto.ui.community

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.test.picsumphoto.data.MainRepository
import com.test.picsumphoto.data.model.Photos
import com.test.picsumphoto.data.network.api.MainService


/**
 * @ClassName PhotosViewModel
 * @Description Photos ViewModel
 * @Author mailo
 * @Date 2021/10/25
 */
class PhotosViewModel(repository: MainRepository) : ViewModel() {
    var dataList = Photos()

    var nextPageUrl: String? = null

    private var requestParamLiveData = MutableLiveData<String>()

    val dataListLiveData = Transformations.switchMap(requestParamLiveData) {
        liveData {
            val resutlt = try {
                val launches = repository.refreshPhotos()
                Result.success(launches)
            } catch (e: Exception) {
                Result.failure<Photos>(e)
            }
            emit(resutlt)
        }
    }

    fun onRefresh() {
        requestParamLiveData.value = MainService.PHOTOS_URL
    }

    fun onLoadMore() {
        requestParamLiveData.value = nextPageUrl ?: ""
    }
}