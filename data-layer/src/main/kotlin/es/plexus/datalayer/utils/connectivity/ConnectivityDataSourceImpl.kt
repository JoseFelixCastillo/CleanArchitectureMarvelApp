package es.plexus.datalayer.utils.connectivity

import android.content.Context
import es.plexus.datalayer.utils.request.isNetworkAvailable

internal class ConnectivityDataSourceImpl(private val context: Context) : ConnectivityDataSource {
    override fun checkNetworkConnectionAvailability() = context.isNetworkAvailable()
}
