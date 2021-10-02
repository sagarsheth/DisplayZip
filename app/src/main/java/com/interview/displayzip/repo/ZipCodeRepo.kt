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

    /*fun getZipCode1(city: String, state: String): Flow<Resource<ZipCodeDTO>> =
        flow {
            //return checkResult(api.getZipCodeListAsync(city, state))
            try {
                emit(Resource.Loading())
                val zip = getZipCode(city, state)
                emit(Resource.Success(zip))
            } catch (e: HttpRetryException) {
                emit(
                    Resource.Error<ZipCodeDTO>(
                        e.localizedMessage ?: "An unexpected error occurred"
                    )
                )
            } catch (e: IOException) {
                emit(
                    Resource.Error<ZipCodeDTO>(
                        e.localizedMessage
                            ?: "Couldn't reach server. Check your internet connection."
                    )
                )
            }
        }*/
}