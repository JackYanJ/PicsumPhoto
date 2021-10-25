package com.test.picsumphoto.data.network

import com.test.picsumphoto.extension.logV
import com.google.gson.GsonBuilder
import com.test.picsumphoto.common.callback.GsonTypeAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.*


/**
 * @ClassName ServiceCreator
 * @Description network basic setting
 * @Author mailo
 * @Date 2021/10/25
 */
object ServiceCreator {
    const val BASE_URL = "https://picsum.photos/v2/"

    val httpClient = OkHttpClient.Builder()
        .addInterceptor(LoggingInterceptor())
        .addInterceptor(HeaderInterceptor())
        .addInterceptor(BasicParamsInterceptor())
        .build()

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().registerTypeAdapterFactory(
            GsonTypeAdapterFactory()
        ).create()))

    private val retrofit = builder.build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    class LoggingInterceptor : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val t1 = System.nanoTime()
            logV(TAG, "Sending request: ${request.url()} \n ${request.headers()}")

            val response = chain.proceed(request)

            val t2 = System.nanoTime()
            logV(TAG, "Received response for  ${response.request().url()} in ${(t2 - t1) / 1e6} ms\n${response.headers()}")
            return response
        }

        companion object {
            const val TAG = "LoggingInterceptor"
        }
    }

    class HeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val request = original.newBuilder().apply {
                header("model", "Android")
                header("If-Modified-Since", "${Date()}")
                header("User-Agent", System.getProperty("http.agent") ?: "unknown")
            }.build()
            return chain.proceed(request)
        }
    }

    class BasicParamsInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val originalHttpUrl = originalRequest.url()
            val url = originalHttpUrl.newBuilder().apply {
//                addQueryParameter("udid", GlobalUtil.getDeviceSerial()) add some general params
            }.build()
            val request = originalRequest.newBuilder().url(url).method(originalRequest.method(), originalRequest.body()).build()
            return chain.proceed(request)
        }
    }
}