package me.stasiak.loginacctivity

import dagger.Component
import me.stasiak.loginacctivity.ui.login.LoginActivity
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {
    fun inject(app: LoginActivity)
}