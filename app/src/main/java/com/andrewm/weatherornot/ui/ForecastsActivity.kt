package com.andrewm.weatherornot.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.andrewm.weatherornot.R
import com.andrewm.weatherornot.databinding.ActivityMyLocationsBinding
import com.andrewm.weatherornot.ui.base.BaseActivity
import com.andrewm.weatherornot.ui.locations.ForecastsView
import com.andrewm.weatherornot.ui.locations.recyclerview.ForecastsAdapter
import com.andrewm.weatherornot.ui.locations.recyclerview.IForecastsViewModel
import javax.inject.Inject

class ForecastsActivity : BaseActivity<ActivityMyLocationsBinding, IForecastsViewModel>(), ForecastsView {

    @Inject
    lateinit var adapter: ForecastsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAndBindContentView(savedInstanceState, R.layout.activity_my_locations)

        setSupportActionBar(binding.toolbar)

        binding.recyclerViewLocations.setHasFixedSize(true)
        binding.recyclerViewLocations.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewLocations.adapter = adapter

        viewModel.reloadData()
    }

    override fun onRefresh(success: Boolean) {
        binding.swipeToRefresh.isRefreshing = false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_forecasts, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}