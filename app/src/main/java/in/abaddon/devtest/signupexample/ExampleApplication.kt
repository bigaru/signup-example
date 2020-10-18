package `in`.abaddon.devtest.signupexample

import `in`.abaddon.devtest.signupexample.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class ExampleApplication: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent
            .builder()
            .build()

        appComponent.inject(this)

        return appComponent
    }
}
