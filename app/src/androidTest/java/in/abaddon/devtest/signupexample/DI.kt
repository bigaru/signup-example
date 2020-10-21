package `in`.abaddon.devtest.signupexample

import `in`.abaddon.devtest.signupexample.di.ActivitiesModule
import `in`.abaddon.devtest.signupexample.di.ViewModelModule
import `in`.abaddon.devtest.signupexample.model.User
import `in`.abaddon.devtest.signupexample.model.UserDao
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ActivitiesModule::class,
    ViewModelModule::class,
    GeneralModule::class
])
interface TestAppComponent: AndroidInjector<DaggerApplication> {
}


@Module
class GeneralModule() {
    @Provides
    fun provideValidator(): Validator{
        return SimpleValidator()
    }

    @Provides
    fun provideDao(): UserDao {
        return FakeUserDao()
    }

    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}

class FakeUserDao: UserDao {
    var counter = 42L

    override fun getAll(): List<User> {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): User? {
        return User(0, "Felix Muster", "Felix@muster.ch", "15.5.1950")
    }

    override fun insert(user: User): Long {
        return counter++

    }

    override fun delete(user: User) {
        TODO("Not yet implemented")
    }

}
