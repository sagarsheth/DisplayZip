package com.interview.displayzip.api

import com.interview.displayzip.models.ZipCodeDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit API Zipcode interface
 */
interface ZipCodeAPI {

    @GET("city-zips.json/{city}/{state}")
    suspend fun getZipCode(
        @Path("city") city: String,
        @Path("state") state: String
    ): Response<ZipCodeDTO>
}