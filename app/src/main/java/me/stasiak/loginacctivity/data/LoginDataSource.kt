package me.stasiak.loginacctivity.data

import arrow.core.Either
import me.stasiak.loginacctivity.data.model.LoggedInUser
import java.io.IOException
import java.util.*

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Either<IOException, LoggedInUser> {
        return try {
            // TODO: handle loggedInUser authentication
            Either.right(LoggedInUser(UUID.randomUUID().toString(), "$username (${password.length})"))
        } catch (e: Throwable) {
            Either.left(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}

