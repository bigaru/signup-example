package `in`.abaddon.devtest.signupexample.di

import `in`.abaddon.devtest.signupexample.SimpleValidator
import `in`.abaddon.devtest.signupexample.Validator
import `in`.abaddon.devtest.signupexample.model.UserDB
import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides


@Module
class GeneralModule(val application: Application) {
    val db: UserDB

    init {
        db = Room.databaseBuilder(application, UserDB::class.java, "database-name").build()
    }

    @Provides
    fun provideValidator(): Validator{
        return SimpleValidator()
    }

    @Provides
    fun provideContext(): Context{
        return application
    }

    @Provides
    fun provideDao(): UserDB {
        return db
    }
}
