package com.interview.displayzip.repo

import androidx.annotation.WorkerThread
import com.interview.displayzip.common.Resource
import com.interview.displayzip.models.ZipCodeDTO
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

/**
 * Interface to be implemented by any Zipcode repository (API/DB)
 */
interface IZipCodeRepo {

    @WorkerThread
    suspend fun getZipCode(city: String, state: String): Response<ZipCodeDTO>
}