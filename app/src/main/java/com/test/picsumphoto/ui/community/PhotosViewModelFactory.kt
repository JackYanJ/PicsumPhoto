package com.test.picsumphoto.ui.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.picsumphoto.data.MainRepository


/**
 * @ClassName PhotosViewModelFactory
 * @Description ViewModel Factory
 * @Author mailo
 * @Date 2021/10/25
 */
class PhotosViewModelFactory(val repository: MainRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PhotosViewModel(repository) as T
    }
}