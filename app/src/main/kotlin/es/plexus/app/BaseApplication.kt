package es.plexus.app

import android.app.Application
import es.plexus.datalayer.di.dataLayerModule
import es.plexus.domainlayer.di.domainLayerModules
import es.plexus.presentationlayer.di.presentationLayerModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(dataLayerModule + domainLayerModules + presentationLayerModule)
        }
    }
}
