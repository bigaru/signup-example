package `in`.abaddon.devtest.signupexample

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class ExampleApplication: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerTestAppComponent
            .builder()
            .build()

        appComponent.inject(this)

        return appComponent
    }
}
