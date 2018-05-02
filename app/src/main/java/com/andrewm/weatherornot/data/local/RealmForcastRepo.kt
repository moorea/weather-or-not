package com.andrewm.weatherornot.data.local

import com.andrewm.weatherornot.data.model.forecast.Forecast
import io.reactivex.Flowable
import io.realm.Realm
import javax.inject.Inject
import javax.inject.Provider

class RealmForecastRepo
@Inject
constructor(private val realmProvider: Provider<Realm>) : ForecastRepo {

    override fun getByField(field: String?, value: String?, detached: Boolean): Forecast? {
        realmProvider.get().use { realm ->
            var realmForecast: Forecast? = realm.where(Forecast::class.java).equalTo(field, value).findFirst()
            if (detached && realmForecast != null) {
                realmForecast = realm.copyFromRealm(realmForecast)
            }
            return realmForecast
        }
    }

    override fun getAllForecasts(): Flowable<List<Forecast>> {
        realmProvider.get().use { realm ->
            return realm.where(Forecast::class.java).findAllAsync().asFlowable()
                    .filter{ it.isLoaded }
                    .map { it }
        }
    }

    override fun save(forecast: Forecast) {
        realmProvider.get().use { realm ->
            realm.executeTransaction { r -> r.copyToRealmOrUpdate(forecast) }
        }
    }

    override fun delete(forecast: Forecast) {
        if (forecast.isValid) {
            realmProvider.get().use { realm ->
                realm.executeTransaction { r ->
                    forecast.deleteFromRealm()
                }
            }
        }
    }

    override fun detach(forecast: Forecast): Forecast {
        if (forecast.isManaged) {
            realmProvider.get().use { realm -> return realm.copyFromRealm(forecast) }
        } else {
            return forecast
        }
    }
}
