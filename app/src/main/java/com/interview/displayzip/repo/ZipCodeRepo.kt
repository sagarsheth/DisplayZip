package com.interview.displayzip.repo

import com.interview.displayzip.api.ZipCodeAPI
import com.interview.displayzip.common.Constants
import com.interview.displayzip.models.ZipCodeDTO
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ZipCodeRepo(private val api: ZipCodeAPI) : IZipCodeRepo {
    companion object {
        fun newInstance(): ZipCodeRepo {
            val api: ZipCodeAPI = Retrofit.Builder().baseUrl(Constants.BASE_URL + Constants.API_KEY)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ZipCodeAPI::class.java)
            return ZipCodeRepo(api)
        }
    }

    override suspend fun getZipCode(city: String, state: String): Response<ZipCodeDTO> {
        return api.getZipCode(city, state)
    }
}