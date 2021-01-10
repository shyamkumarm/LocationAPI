package com.finbox.locationapi.view


import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkInfo
import com.finbox.locationapi.R
import com.finbox.locationapi.base.BaseActivity
import com.finbox.locationapi.databinding.ActivityMainBinding
import com.finbox.locationapi.model.Location
import com.finbox.locationapi.utils.Constants
import com.finbox.locationapi.viewmodels.BusinessViewModel
import kotlinx.android.synthetic.main.activity_main.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Long
import java.lang.Long.getLong
import java.util.*


class MainActivity : BaseActivity(), View.OnClickListener {
    private lateinit var viewAdapter: ViewAdapter


    private val viewModel by viewModel<BusinessViewModel>()
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater).root
    }

    var isWorkRunning: Boolean = false

    override fun getContentLayout(): View = binding
    override fun onCreate() {

        if (!checkLocationPermission()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsList,
                Constants.PERMISSION_REQUEST_CODE
            )
        }

        initView()
        initObserver()
    }

    private fun initView() {
        isWorkRunning = viewModel.isWorkRunning().also {
            if (it) {
                binding.tracking.text = getString(R.string.stop_track)
            } else {
                binding.tracking.text = getString(R.string.start_track)
            }
            enableLocationGPS()
        }

        binding.tracking.setOnClickListener(this)

        binding.recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            viewAdapter = ViewAdapter(arrayListOf())
            this.adapter = viewAdapter
        }


    }

    private fun initObserver() {
        viewModel.run {
            locationUIData.observe(this@MainActivity, Observer {
                if (it.isNotEmpty()) {
                    it?.let { viewAdapter.run { setData(it) } }
                }
            })

            getNotifyLocation().observe(this@MainActivity, Observer {
                if (it?.state == WorkInfo.State.ENQUEUED) {
                    getLocationList()
                }
            })
            getLocationList()
        }
    }


    override fun onClick(p0: View?) {
        viewModel.run {
            if (!isWorkRunning) {
                startWork()
                displayNotification()
                binding.tracking.text = getString(R.string.stop_track)
            } else {
                stopWork()
                dismissNotification()
                binding.tracking.text = getString(R.string.start_track)
            }

        }
    }

}


