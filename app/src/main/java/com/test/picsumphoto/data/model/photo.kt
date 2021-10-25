package com.test.picsumphoto.data.model


/**
 * @ClassName photo
 * @Description photo entity
 * @Author mailo
 * @Date 2021/10/25
 */

class Photos : ArrayList<Photo>()

data class Photo(
    val author: String,
    val download_url: String,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)