package `in`.abaddon.devtest.signupexample.di

import `in`.abaddon.devtest.signupexample.SimpleValidator
import `in`.abaddon.devtest.signupexample.Validator
import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides


@Module
class GeneralModule(val application: Application) {

    @Provides
    fun provideValidator(ctx: Context): Validator{
        return SimpleValidator(ctx)
    }

    @Provides
    fun provideContext(): Context{
        return application
    }
}
