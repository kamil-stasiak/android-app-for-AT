package me.stasiak.loginacctivity.ui.home

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import me.stasiak.loginacctivity.R

import kotlinx.android.synthetic.main.activity_home.*
import me.stasiak.loginacctivity.App
import me.stasiak.loginacctivity.db.UserDao
import me.stasiak.loginacctivity.ui.login.doAsync
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).appComponent.inject(this)

        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            doAsync {
                val message = userDao.getFirstUser()
                println("+++")
                println(message.toString())
            }
            Snackbar.make(view, "Replace with your own action ", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

}
