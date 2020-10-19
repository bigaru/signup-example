package `in`.abaddon.devtest.signupexample.di

import `in`.abaddon.devtest.signupexample.signup.SignupActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivitiesModule {

    @ContributesAndroidInjector
    fun bindSignupActivity(): SignupActivity

}
