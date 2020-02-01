package me.stasiak.loginacctivity.data

import arrow.core.Either
import me.stasiak.loginacctivity.data.model.LoggedInUser
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Either<IOException, LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "$username (${password.length})")
            return Either.right(fakeUser)
        } catch (e: Throwable) {
            return Either.left(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}

