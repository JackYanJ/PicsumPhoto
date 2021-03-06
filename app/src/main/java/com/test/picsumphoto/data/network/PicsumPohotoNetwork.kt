package com.test.picsumphoto.data.network

import com.test.picsumphoto.data.network.api.MainService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


/**
 * @ClassName PicsumPohotoNetwork
 * @Description manage all request
 * @Author mailo
 * @Date 2021/10/25
 */
class PicsumPohotoNetwork {
    private val mainService = ServiceCreator.create(MainService::class.java)

    suspend fun fetchPhotos() = mainService.getPhotos().await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }
            })
        }
    }

    companion object {

        private var network: PicsumPohotoNetwork? = null

        fun getInstance(): PicsumPohotoNetwork {
            if (network == null) {
                synchronized(PicsumPohotoNetwork::class.java) {
                    if (network == null) {
                        network = PicsumPohotoNetwork()
                    }
                }
            }
            return network!!
        }
    }
}