package `in`.abaddon.devtest.signupexample

import android.content.Context
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
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

    override fun validateBirthday(birthday: String): String? {
        val pattern = DateTimeFormat.forPattern("dd.MM.yyyy")

        try {
            val validDate = DateTime.parse(birthday, pattern)

            if(1900 > validDate.year || validDate.year > 2019) {
                throw IllegalArgumentException()
            }
        } catch (e: Exception) {
            return ctx.getString(R.string.msg_date_invalid)
        }

        return null
    }

}
