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

/**
 * Main UI activity class which interacts with view model to get Zip Codes
 */
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

        // Display Zip code in Grid layout of 3
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
            if (isCityStateEntered(city.toString(), state.toString())) {
                Toast.makeText(applicationContext, "Enter city and state", Toast.LENGTH_LONG).show()
            } else {
                getZipCode(city.toString().trim(), state.toString().trim())
                mSearchButton.hideKeyboard()
            }
        })
    }

    private fun getZipCode(city: String, state: String) {
        mZipCodeViewModel.getZipCodeStatus().observe(this, {
            when (it) {
                is Resource.Success -> {
                    if (it.data == null || it.data.zip_codes.isEmpty()) {
                        mEmptyView.visibility = View.VISIBLE
                        mZipCodeListRecycleView.adapter = null
                    } else {
                        mEmptyView.visibility = View.GONE
                        // set recycle view adapter
                        mZipCodeListRecycleView.adapter =
                            it.data?.let { it1 -> ZipCodeAdapter(it1.zip_codes) }
                    }
                }
                is Resource.Error -> {
                    // set recycle view adapter
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
                    mEmptyView.visibility = View.VISIBLE
                    mZipCodeListRecycleView.adapter = null
                }
            }
        })
        mZipCodeViewModel.getZipCode(city, state)
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun isCityStateEntered(city: String, state: String): Boolean {
        return (city.isEmpty() || state.isEmpty())
    }
}