package com.interview.displayzip.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.interview.displayzip.common.Resource
import com.interview.displayzip.models.ZipCodeDTO
import com.interview.displayzip.repo.IZipCodeRepo
import com.interview.displayzip.repo.ZipCodeRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * ViewModel class get zip data from repository and provide data to UI component
 */
class ZipCodesViewModel(private val zipCodeRepo: IZipCodeRepo = ZipCodeRepo.newInstance()) :
    ViewModel() {

    private val mZipCodeList = MutableLiveData<Resource<ZipCodeDTO>>()

    fun getZipCode(city: String, state: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val result = zipCodeRepo.getZipCode(city, state)
                    if (result.isSuccessful && result.body() != null) {
                        mZipCodeList.postValue(
                            Resource.Success(result.body() ?: ZipCodeDTO(emptyList()))
                        )
                    } else {
                        mZipCodeList.postValue(Resource.Success(ZipCodeDTO(emptyList())))
                    }
                } catch (e: IOException) {
                    mZipCodeList.postValue(
                        Resource.Success(ZipCodeDTO(emptyList()))
                    )
                } catch (e: Exception) {
                    mZipCodeList.postValue(
                        Resource.Success(ZipCodeDTO(emptyList()))
                    )
                }
            }
        }
    }

    fun getZipCodeStatus(): LiveData<Resource<ZipCodeDTO>> {
        return mZipCodeList
    }
}