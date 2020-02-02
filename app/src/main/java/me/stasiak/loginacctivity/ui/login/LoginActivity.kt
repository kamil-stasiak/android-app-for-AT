package me.stasiak.loginacctivity.ui.login

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import me.stasiak.loginacctivity.App
import me.stasiak.loginacctivity.Info
import me.stasiak.loginacctivity.R
import me.stasiak.loginacctivity.db.AppDatabase
import me.stasiak.loginacctivity.db.User
import me.stasiak.loginacctivity.db.UserDao
import me.stasiak.loginacctivity.ui.home.HomeActivity
import javax.inject.Inject

class doAsync(val handler: () -> Unit) : AsyncTask<Void, Void, Void>() {
    override fun doInBackground(vararg params: Void?): Void? {
        handler()
        return null
    }
}

class LoginActivity : AppCompatActivity() {

    // not required, added only for aliases
    val usernameInput: EditText by lazy { findViewById<EditText>(R.id.loginViewUsernameInput) }
    val passwordInput: EditText by lazy { findViewById<EditText>(R.id.loginViewPasswordInput) }
    val loginButton: Button by lazy { findViewById<Button>(R.id.loginViewLoginButton) }
    val loadingIndicator: ProgressBar by lazy { findViewById<ProgressBar>(R.id.loadingIndicator) }
    val infoAlias: TextView by lazy { findViewById<TextView>(R.id.infoTextView) }

    @Inject
    lateinit var loginViewModel: LoginViewModel

    @Inject
    lateinit var userDao: UserDao

    @Inject
    lateinit var database: AppDatabase

    // for singleton test
    @Inject
    lateinit var info: Info

    @Inject
    lateinit var info2: Info

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).appComponent.inject(this)

        setContentView(R.layout.activity_login)

        // Alias example, both infoTextView (@+id/infoTextView) and infoAlias (findViewById) works
        // infoTextView.text = "Is info singleton? ${info === info2}"
//        infoAlias.text = "Is info singleton? ${info === info2}"
//        infoAlias.text = database.toString()

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            loginButton.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                usernameInput.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                passwordInput.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loadingIndicator.visibility = View.GONE

            loginResult.fold(
                { showLoginFailed(it) },
                {
                    updateUiWithUser(it)
                    doAsync {
                        userDao.deleteAll()
                        println("Writing start")
                        userDao.insert(User(1, "login", it.displayName))
                        println("Writing end")
                        val intent = Intent(this, HomeActivity::class.java);
                        startActivity(intent)

                    }.execute()
//                    runBlocking {
//                        userDao.insert(User(1, "login", it.displayName))
//                    }
                })
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        usernameInput.afterTextChanged {
            loginViewModel.loginDataChanged(
                usernameInput.text.toString(),
                passwordInput.text.toString()
            )
        }

        passwordInput.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    usernameInput.text.toString(),
                    passwordInput.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            usernameInput.text.toString(),
                            passwordInput.text.toString()
                        )
                }
                false
            }

            loginButton.setOnClickListener {
                loadingIndicator.visibility = View.VISIBLE
                loginViewModel.login(usernameInput.text.toString(), passwordInput.text.toString())
            }
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
