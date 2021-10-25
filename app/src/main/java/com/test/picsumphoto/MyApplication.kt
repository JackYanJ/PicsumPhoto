package com.test.picsumphoto

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.test.picsumphoto.common.view.NoStatusFooter


/**
 * @ClassName MyApplication
 * @Description custom Application
 * @Author mailo
 * @Date 2021/10/25
 */
class MyApplication : Application() {
    init {
        SmartRefreshLayout.setDefaultRefreshInitializer { context, layout ->
            layout.setEnableLoadMore(true)
            layout.setEnableLoadMoreWhenContentNotFull(true)
        }

        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setEnableHeaderTranslationContent(true)
            MaterialHeader(context).setColorSchemeResources(
                R.color.purple_200,
                R.color.purple_200,
                R.color.purple_200
            )
        }

        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            layout.setEnableFooterFollowWhenNoMoreData(true)
            layout.setEnableFooterTranslationContent(true)
            layout.setFooterHeight(153f)
            layout.setFooterTriggerRate(0.6f)
            NoStatusFooter.REFRESH_FOOTER_NOTHING = "- The End -"
            NoStatusFooter(context).apply {
                setAccentColorId(R.color.purple_500)
                setTextTitleSize(16f)
            }
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        lateinit var context: Context
    }
}