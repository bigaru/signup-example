package `in`.abaddon.devtest.signupexample

import `in`.abaddon.devtest.signupexample.di.DaggerAppComponent
import `in`.abaddon.devtest.signupexample.di.GeneralModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import net.danlew.android.joda.JodaTimeAndroid

class ExampleApplication: DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent
            .builder()
            .generalModule(GeneralModule(this))
            .build()

        appComponent.inject(this)

        return appComponent
    }
}
