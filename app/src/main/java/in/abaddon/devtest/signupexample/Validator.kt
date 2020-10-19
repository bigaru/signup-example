package `in`.abaddon.devtest.signupexample

import android.content.Context
import javax.inject.Inject

interface Validator {
    /**
     * If it is valid, it returns null
     * Otherwise it returns the corresponding error msg
     */
    fun validateName(name: String): String?

    /**
     * If it is valid, it returns null
     * Otherwise it returns the corresponding error msg
     */
    fun validateEmail(email: String): String?

    /**
     * Expected format dd.mm.yyyy
     * It is valid from 1. January 1900 to 31. December 2019
     * If it is valid, it returns null
     * Otherwise it returns the corresponding error msg
     */
    fun validateBirthday(birthday: String): String?
}

class SimpleValidator @Inject constructor(val ctx: Context): Validator {
    override fun validateName(name: String): String? {
        if(name.isBlank()) {
            return ctx.getString(R.string.msg_is_required)
        }
        return null
    }

    override fun validateEmail(email: String): String? {
        val regex = "^\\S+@\\S+\\.\\S{2,}$".toRegex()
        if(!email.trim().matches(regex)){
            return ctx.getString(R.string.msg_email_invalid)
        }

        return null
    }

    // TODO better date validator
    override fun validateBirthday(birthday: String): String? {
        val regex = "^(0?[1-9]|[12][0-9]|3[01])\\.(0?[1-9]|1[012])\\.(19\\d\\d|20[01][0-9])$".toRegex()
        if(!birthday.trim().matches(regex)){
            return ctx.getString(R.string.msg_date_invalid)
        }

        return null
    }

}
