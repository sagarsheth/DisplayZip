package com.interview.displayzip

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.interview.displayzip.common.Resource
import com.interview.displayzip.databinding.ActivityDisplayzipBinding
import com.interview.displayzip.ui.ZipCodeAdapter
import com.interview.displayzip.viewmodel.ZipCodesViewModel

class ActivityDisplayZip : AppCompatActivity() {
    private lateinit var mZipCodeViewModel: ZipCodesViewModel
    private lateinit var mZipCodeListRecycleView: RecyclerView
    private lateinit var mSearchButton: Button
    private lateinit var mEmptyView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDisplayzipBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mZipCodeViewModel = ViewModelProvider(this).get(ZipCodesViewModel::class.java)
        initUI(binding)
    }

    private fun initUI(binding: ActivityDisplayzipBinding) {
        mZipCodeListRecycleView = binding.zipcodeList
        mSearchButton = binding.searchButton
        mEmptyView = binding.emptyView

        GridLayoutManager(
            this, // context
            3, // span count
            RecyclerView.VERTICAL, // orientation
            false // reverse layout
        ).apply {
            // specify the layout manager for recycler view
            mZipCodeListRecycleView.layoutManager = this
        }

        mSearchButton.setOnClickListener(View.OnClickListener {
            val city = binding.cityEditText.text
            val state = binding.stateEditText.text
            if (city?.isEmpty() == true || state?.isEmpty() == true) {
                Toast.makeText(applicationContext, "Enter city and state", Toast.LENGTH_LONG).show()
            } else {
                mEmptyView.visibility = View.GONE
                getZipCode(city.toString(), state.toString())
            }
        })
    }

    private fun getZipCode(city: String, state: String) {
        mZipCodeViewModel.getZipCode(city, state).observe(this, {
            when (it) {
                is Resource.Success -> {
                    if (it.data == null || it.data.zip_codes.isEmpty()) {
                        mEmptyView.visibility = View.VISIBLE
                    }
                    // set recycle view adapter
                    mZipCodeListRecycleView.adapter =
                        it.data?.let { it1 -> ZipCodeAdapter(it1.zip_codes) }
                    mSearchButton.hideKeyboard()
                }
                is Resource.Error -> {
                    // set recycle view adapter
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
                    mEmptyView.visibility = View.VISIBLE
                }
                is Resource.Loading -> {
                    // set recycle view adapter
                    Toast.makeText(applicationContext, "Loading Please wait...", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}