package com.interview.displayzip.repo

import com.interview.displayzip.models.ZipCodeDTO
import retrofit2.Response

/**
 * Fake repository for testing to reduce API calls
 */
class FakeZipCodeRepo : IZipCodeRepo {
    private var zipList: List<String> = arrayListOf("123", "222")
    override suspend fun getZipCode(city: String, state: String): Response<ZipCodeDTO> {
        return Response.success(ZipCodeDTO(zipList))
    }

    fun setFakeResult(list: List<String>) {
        zipList = list
    }
}