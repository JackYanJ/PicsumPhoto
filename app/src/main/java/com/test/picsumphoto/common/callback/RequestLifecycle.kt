package com.test.picsumphoto.common.callback


/**
 * @ClassName RequestLifecycle
 * @Description requestLifeCycle
 * @Author mailo
 * @Date 2021/10/25
 */
interface RequestLifecycle {
    fun startLoading()

    fun loadFinished()

    fun loadFailed(msg: String?)


}