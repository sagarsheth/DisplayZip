package com.interview.displayzip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.interview.displayzip.common.Resource
import com.interview.displayzip.viewmodel.ZipCodesViewModel

class DisplayZipActivity : AppCompatActivity() {
    private lateinit var mZipCodeViewModel: ZipCodesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_zip)
        mZipCodeViewModel = ViewModelProvider(this).get(ZipCodesViewModel::class.java)
        // initUI()
        mZipCodeViewModel.getZipCode("Overland Park", "KS").observe(this, {
            when (it) {
                is Resource.Success -> {
                    // set recycle view adapter
                    Toast.makeText(applicationContext, it.data.toString(), Toast.LENGTH_LONG)
                        .show()
                }
                is Resource.Error -> {
                    // set recycle view adapter
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                    // set recycle view adapter
                    Toast.makeText(applicationContext, "Loading Please wait...", Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }

    /*  private fun initUI() {
          TODO("Not yet implemented")
      }*/
}