package com.interview.displayzip.models

import com.google.gson.annotations.SerializedName

/**
 * Data calls DTO for ZipCode
 */
data class ZipCodeDTO(
    @SerializedName("zip_codes")
    val zip_codes: List<String>
)