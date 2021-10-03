package com.interview.displayzip.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.interview.displayzip.TestCoroutineRule
import com.interview.displayzip.repo.FakeZipCodeRepo
import com.interview.displayzip.repo.ZipCodeRepo
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotSame
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * View Model test case class with Fake and API repository
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ZipCodesViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var fakeZipCodeRepo: FakeZipCodeRepo
    private lateinit var viewModelTest: ZipCodesViewModel

    @Before
    fun setUp() {
        fakeZipCodeRepo = FakeZipCodeRepo()
        viewModelTest = ZipCodesViewModel(fakeZipCodeRepo)
    }

    @Test
    fun testGetZip_DummyData_Success() {
        val resultList: ArrayList<String> = arrayListOf("123", "222")
        fakeZipCodeRepo.setFakeResult(arrayListOf("123", "222"))
        testCoroutineRule.runBlockingTest {
            viewModelTest.getZipCode("asd", "asd")
            assertEquals(
                resultList,
                viewModelTest.getZipCodeStatus().getOrAwaitValue().data?.zip_codes
            )
        }
    }

    @Test
    fun testGetZip_DummyData_NoResult_Fail() {
        fakeZipCodeRepo.setFakeResult(emptyList())
        testCoroutineRule.runBlockingTest {
            viewModelTest.getZipCode("asd", "asd")
            assertNotSame(
                "No results found",
                viewModelTest.getZipCodeStatus().getOrAwaitValue().message
            )
        }
    }

    @Test
    fun testGetZip_RealData_success() {
        val resultList: ArrayList<String> = arrayListOf(
            "66204",
            "66207",
            "66210",
            "66212",
            "66213",
            "66214",
            "66221",
            "66223",
            "66224",
            "66225",
            "66251",
            "66282",
            "66283",
            "66062",
            "66085",
            "66201",
            "66202",
            "66203",
            "66206",
            "66208",
            "66209",
            "66211",
            "66215"
        )
        val viewModel = ZipCodesViewModel(ZipCodeRepo.newInstance())
        testCoroutineRule.runBlockingTest {
            viewModel.getZipCode("Overland Park", "KS")
            assertNotSame(
                resultList,
                viewModel.getZipCodeStatus().getOrAwaitValue().data?.zip_codes
            )
        }
    }

    @Test
    fun testGetZip_RealData_InvalidCity_Fail() {
        val viewModel = ZipCodesViewModel(ZipCodeRepo.newInstance())
        testCoroutineRule.runBlockingTest {
            viewModel.getZipCode("abc", "MA")
            assertNotSame(
                "No results found",
                viewModel.getZipCodeStatus().getOrAwaitValue().message
            )
        }
    }

    @Test
    fun testGetZip_RealData_InvalidState_Fail() {
        val viewModel = ZipCodesViewModel(ZipCodeRepo.newInstance())
        testCoroutineRule.runBlockingTest {
            viewModel.getZipCode("Pittsburgh", "RR")
            assertNotSame(
                "No results found",
                viewModel.getZipCodeStatus().getOrAwaitValue().message
            )
        }
    }

    @Test
    fun testGetZip_RealData_EmptyCity_Fail() {
        val viewModel = ZipCodesViewModel(ZipCodeRepo.newInstance())
        testCoroutineRule.runBlockingTest {
            viewModel.getZipCode("", "RR")
            assertNotSame(
                "No results found",
                viewModel.getZipCodeStatus().getOrAwaitValue().message
            )
        }
    }

    @Test
    fun testGetZip_RealData_EmptyState_Fail() {
        val viewModel = ZipCodesViewModel(ZipCodeRepo.newInstance())
        testCoroutineRule.runBlockingTest {
            viewModel.getZipCode("Pittsburgh", "")
            assertNotSame(
                "No results found",
                viewModel.getZipCodeStatus().getOrAwaitValue().message
            )
        }
    }
}