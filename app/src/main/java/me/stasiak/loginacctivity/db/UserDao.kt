package me.stasiak.loginacctivity.db

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

/*
Return type of query could be java or guava optional
Queries are synchronous - room throws exception if you run them on main thread
Queries are asynchronous if return type is:
- LiveData / Flowable
- Maybe / Single (RxJava)

LiveData queries are observable queries - it will notify you
when data in database updates
 */
@Dao
interface UserDao {

    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("delete from users")
    fun deleteAll();

    // Only for training
    @Query("select * from users")
    fun getAllUser(): List<User>

    @Query("select * from users limit 1")
    fun getFirstUser(): User

    // TODO To more
    @RawQuery
    fun getUserViaQuery(query: SupportSQLiteQuery): User
}