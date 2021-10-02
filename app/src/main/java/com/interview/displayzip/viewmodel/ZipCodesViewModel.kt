package com.interview.displayzip.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.interview.displayzip.common.Resource
import com.interview.displayzip.models.ZipCodeDTO
import com.interview.displayzip.repo.ZipCodeRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class ZipCodesViewModel : ViewModel() {
    private val zipCodeRepo: ZipCodeRepo = ZipCodeRepo.newInstance()
    private val mZipCodeList = MutableLiveData<Resource<ZipCodeDTO>>()


    fun getZipCode(city: String, state: String): LiveData<Resource<ZipCodeDTO>> {
        viewModelScope.launch {
            mZipCodeList.value = Resource.Loading()
            withContext(Dispatchers.IO) {
                try {
                    val result = zipCodeRepo.getZipCode(city, state)
                    if (result.isSuccessful && result.body() != null) {
                        withContext(Dispatchers.Main) {
                            mZipCodeList.value =
                                Resource.Success(result.body() ?: ZipCodeDTO(ArrayList()))
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            mZipCodeList.value = Resource.Error(result.message())
                        }
                    }
                } catch (e: IOException) {
                    withContext(Dispatchers.Main) {
                        mZipCodeList.value = Resource.Error(e.localizedMessage)
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        mZipCodeList.value = Resource.Error(e.localizedMessage)
                    }
                }
            }
        }
        return mZipCodeList
    }
}