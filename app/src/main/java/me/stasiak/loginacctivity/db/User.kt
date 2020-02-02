package me.stasiak.loginacctivity.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// It is useless because we got only 1 user,
// only for training
@Entity(tableName = "users")
data class User(

    @PrimaryKey
    @ColumnInfo(name = "uid")
    val uid: Int,

    @ColumnInfo(name = "login")
    val login: String,

    @ColumnInfo(name = "display_name")
    val displayName: String
)