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

class ZipCodesViewModel : ViewModel() {
    private val zipCodeRepo: ZipCodeRepo = ZipCodeRepo.newInstance()
    private val mZipCodeList = MutableLiveData<Resource<ZipCodeDTO>>()


    fun getZipCode(city: String, state: String): LiveData<Resource<ZipCodeDTO>> {
        viewModelScope.launch {
            mZipCodeList.value = Resource.Loading()
            withContext(Dispatchers.IO) {
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
                /*zipCodeRepo.getZipCode1(city, state).onEach { result ->
                    when (result) {
                        is Resource.Success -> mZipCodeList.postValue(Resource.Success(data = result.data as ZipCodeDTO))
                        is Resource.Error -> mZipCodeList.postValue(result.message?.let {
                            Resource.Error(
                                message = it
                            )
                        })
                        is Resource.Loading -> mZipCodeList.postValue(Resource.Loading())
                    }
                }.launchIn(viewModelScope)*/
            }
        }
        return mZipCodeList
    }
}