package `in`.abaddon.devtest.signupexample.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): List<User>

    @Query("SELECT * FROM users WHERE id LIKE :id")
    fun findById(id: Int): User

    @Insert
    fun insert(user: User): Long

    @Delete
    fun delete(user: User)
}
