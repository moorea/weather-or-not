package com.andrewm.weatherornot.data.local

import com.andrewm.weatherornot.data.model.forecast.Forecast
import io.reactivex.Flowable

interface ForecastRepo {
    fun getByField(field: String?, value: String?, detached: Boolean): Forecast?
    fun getAllForecasts(): Flowable<List<Forecast>>
    fun save(forecast: Forecast)
    fun delete(forecast: Forecast)
    fun detach(forecast: Forecast): Forecast
}