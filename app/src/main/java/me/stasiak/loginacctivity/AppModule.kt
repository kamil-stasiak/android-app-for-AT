package me.stasiak.loginacctivity

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import me.stasiak.loginacctivity.data.LoginDataSource
import me.stasiak.loginacctivity.data.LoginRepository
import me.stasiak.loginacctivity.db.AppDatabase
import me.stasiak.loginacctivity.db.UserDao
import me.stasiak.loginacctivity.ui.login.LoginViewModel
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun sayLoveDagger2(): Info {
        return Info("Text from AppModule")
    }

    @Provides
    @Singleton
    fun provideLoginDataSource(): LoginDataSource {
        return LoginDataSource()
    }

    @Provides
    @Singleton
    fun provideLoginRepository(loginDataSource: LoginDataSource): LoginRepository {
        return LoginRepository(loginDataSource);
    }

    @Provides
    @Singleton
    fun provideLoginViewModel(loginRepository: LoginRepository): LoginViewModel {
        return LoginViewModel(loginRepository);
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "app-database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }
}