package ampuero.joaquin.ui

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.internal.modules.ApplicationContextModule

@HiltAndroidApp
class MovieDBApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}

