package me.stasiak.loginacctivity

import android.app.Application


class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .context(this)
            .build()
    }
}