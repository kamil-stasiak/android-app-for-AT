package me.stasiak.loginacctivity

import dagger.Module
import dagger.Provides
import me.stasiak.loginacctivity.data.LoginDataSource
import me.stasiak.loginacctivity.data.LoginRepository
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
    fun getLoginDataSource(): LoginDataSource {
        return LoginDataSource()
    }

    @Provides
    @Singleton
    fun getLoginRepository(loginDataSource: LoginDataSource): LoginRepository {
        return LoginRepository(loginDataSource);
    }

    @Provides
    @Singleton
    fun getLoginViewModel(loginRepository: LoginRepository): LoginViewModel {
        return LoginViewModel(loginRepository);
    }
}