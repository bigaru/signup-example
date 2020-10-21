package `in`.abaddon.devtest.signupexample.di

import `in`.abaddon.devtest.signupexample.SimpleValidator
import `in`.abaddon.devtest.signupexample.Validator
import `in`.abaddon.devtest.signupexample.model.UserDB
import `in`.abaddon.devtest.signupexample.model.UserDao
import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


@Module
class GeneralModule(application: Application) {
    val db: UserDB

    init {
        db = Room.databaseBuilder(application, UserDB::class.java, "database-name").build()
    }

    @Provides
    fun provideValidator(): Validator{
        return SimpleValidator()
    }

    @Provides
    fun provideDao(): UserDao {
        return db.userDao()
    }

    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}
