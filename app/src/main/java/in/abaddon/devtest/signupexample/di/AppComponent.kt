package `in`.abaddon.devtest.signupexample.di

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ActivitiesModule::class,
    ViewModelModule::class
])
interface AppComponent: AndroidInjector<DaggerApplication> {
}
