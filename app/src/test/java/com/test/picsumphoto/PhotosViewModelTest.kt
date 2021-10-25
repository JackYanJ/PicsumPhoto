package com.test.picsumphoto

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mindorks.example.coroutines.utils.TestCoroutineRule
import com.test.picsumphoto.data.MainRepository
import com.test.picsumphoto.data.model.Photo
import com.test.picsumphoto.ui.community.PhotosViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import kotlin.Result.Companion.failure


/**
 * @ClassName PhotosViewModelTest
 * @Description TODO
 * @Author mailo
 * @Date 2021/10/25
 */
class PhotosViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var mainRespository: MainRepository

    @Mock
    private lateinit var apiUsersObserver: Observer<Result<List<Photo>>>

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            doReturn(emptyList<Photo>())
                .`when`(mainRespository)
                .refreshPhotos()
            val viewModel = PhotosViewModel(mainRespository)
            viewModel.dataListLiveData.observeForever(apiUsersObserver)
            verify(apiUsersObserver).onChanged(Result.success(emptyList()))
            viewModel.dataListLiveData.removeObserver(apiUsersObserver)
        }
    }

    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Message For You"
            doThrow(RuntimeException(errorMessage))
                .`when`(mainRespository)
                .refreshPhotos()
            val viewModel = PhotosViewModel(mainRespository)
            viewModel.dataListLiveData.observeForever(apiUsersObserver)
            verify(mainRespository).refreshPhotos()
            verify(apiUsersObserver).onChanged(
                failure(
                    RuntimeException(errorMessage)
                )
            )
            viewModel.dataListLiveData.removeObserver(apiUsersObserver)
        }
    }

}
