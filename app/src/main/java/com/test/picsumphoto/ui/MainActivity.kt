package com.test.picsumphoto.ui

import android.os.Bundle
import android.view.View
import android.view.ViewStub
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spacex.extension.showToast
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.test.picsumphoto.R
import com.test.picsumphoto.common.callback.RequestLifecycle
import com.test.picsumphoto.common.view.SimpleDividerDecoration
import com.test.picsumphoto.extension.gone
import com.test.picsumphoto.extension.visible
import com.test.picsumphoto.ui.community.PhotosAdapter
import com.test.picsumphoto.ui.community.PhotosViewModel
import com.test.picsumphoto.util.GlobalUtil
import com.test.picsumphoto.util.InjectorUtil
import com.test.picsumphoto.util.ResponseHandler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RequestLifecycle {

    /**
     * if has loaded data
     */
    private var mHasLoadedData = false

    /**
     * error view
     */
    private var loadErrorView: View? = null

    /**
     * root view
     */
    protected var rootView: View? = null

    /**
     * loading view
     */
    protected var loading: ProgressBar? = null

    /**
     * log tag
     */
    protected val TAG: String = this.javaClass.simpleName

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            InjectorUtil.getPhotosViewModelFactory()
        ).get(PhotosViewModel::class.java)
    }

    private lateinit var adapter: PhotosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loading = findViewById(R.id.loading)

        adapter = PhotosAdapter(this, viewModel.dataList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(SimpleDividerDecoration(this))
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = null
        refreshLayout.setOnRefreshListener { viewModel.onRefresh() }
        refreshLayout.gone()
        observe()

    }

    override fun onResume() {
        super.onResume()
        if (!mHasLoadedData) {
            loadDataOnce()
            mHasLoadedData = true
        }
    }

    /**
     * loading start
     */
    @CallSuper
    override fun startLoading() {
        loading?.visibility = View.VISIBLE
        hideLoadErrorView()
        viewModel.onRefresh()
        refreshLayout.gone()
    }

    /**
     * loading finished
     */
    @CallSuper
    override fun loadFinished() {
        loading?.visibility = View.GONE
        refreshLayout.visible()
    }

    /**
     * loading fail
     */
    @CallSuper
    override fun loadFailed(msg: String?) {
        loading?.visibility = View.GONE
        showLoadErrorView(msg ?: GlobalUtil.getString(R.string.unknown_error)) { startLoading() }
        refreshLayout.visible()
    }


    open fun loadDataOnce() {
        startLoading()
    }

    /**
     * return error information to user
     *
     * @param tip
     * @param block
     */
    protected fun showLoadErrorView(tip: String, block: View.() -> Unit) {
        if (loadErrorView != null) {
            loadErrorView?.visibility = View.VISIBLE
            return
        }
        if (rootView != null) {
            val viewStub = rootView?.findViewById<ViewStub>(R.id.loadErrorView)
            if (viewStub != null) {
                loadErrorView = viewStub.inflate()
                val loadErrorText = loadErrorView?.findViewById<TextView>(R.id.loadErrorText)
                loadErrorText?.text = tip
                val loadErrorkRootView = loadErrorView?.findViewById<View>(R.id.loadErrorkRootView)
                loadErrorkRootView?.setOnClickListener {
                    it?.block()
                }
            }
        }
    }

    /**
     * 将load error view进行隐藏。
     */
    protected fun hideLoadErrorView() {
        loadErrorView?.visibility = View.GONE
    }

    private fun observe() {
        viewModel.dataListLiveData.observe(this, Observer { result ->
            val response = result.getOrNull()
            if (response == null) {
                ResponseHandler.getFailureTips(result.exceptionOrNull())
                    .let { if (viewModel.dataList.isNullOrEmpty()) loadFailed(it) else it.showToast() }
                refreshLayout.closeHeaderOrFooter()
                return@Observer
            }
            loadFinished()
            if (response.isNullOrEmpty() && viewModel.dataList.isEmpty()) {
                refreshLayout.closeHeaderOrFooter()
                return@Observer
            }
            if (response.isNullOrEmpty() && viewModel.dataList.isNotEmpty()) {
                refreshLayout.finishLoadMoreWithNoMoreData()
                return@Observer
            }
            when (refreshLayout.state) {
                RefreshState.None, RefreshState.Refreshing -> {
                    val itemCount = viewModel.dataList.size
                    viewModel.dataList.addAll(response)
                    adapter.notifyItemRangeInserted(itemCount, response.size)
                }
                else -> {
                }
            }
            refreshLayout.finishLoadMoreWithNoMoreData()
        })
    }
}