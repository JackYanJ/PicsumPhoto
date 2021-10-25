package com.test.picsumphoto.data

import com.test.picsumphoto.data.network.PicsumPohotoNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext


/**
 * @ClassName mainRepository
 * @Description manage repository data
 * @Author mailo
 * @Date 2021/10/25
 */
class MainRepository private constructor(private val picsumPohotoNetwork: PicsumPohotoNetwork) {
    suspend fun refreshPhotos() = requestPhotos()

    private suspend fun requestPhotos() = withContext(Dispatchers.IO) {
        val response = picsumPohotoNetwork.fetchPhotos()
        response
    }

    companion object {

        private var repository: MainRepository? = null

        fun getInstance(network: PicsumPohotoNetwork): MainRepository {
            if (repository == null) {
                synchronized(MainRepository::class.java) {
                    if (repository == null) {
                        repository = MainRepository(network)
                    }
                }
            }

            return repository!!
        }
    }
}