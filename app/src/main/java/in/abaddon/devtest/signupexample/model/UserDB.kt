package `in`.abaddon.devtest.signupexample.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(User::class), version = 1)
abstract class UserDB : RoomDatabase() {
    abstract fun userDao(): UserDao
}
