package me.stasiak.loginacctivity

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import me.stasiak.loginacctivity.ui.home.HomeActivity
import me.stasiak.loginacctivity.ui.login.LoginActivity
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun inject(app: LoginActivity)
    fun inject(app: HomeActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

    fun context(): Context
    fun applicationContext(): Application
}