package `in`.abaddon.devtest.signupexample.signup

sealed class Action()
data class NameEntered(val newValue: String): Action()
data class NameValidated(val msg: String?): Action()

data class EmailEntered(val newValue: String): Action()
data class EmailValidated(val msg: String?): Action()

data class BirthdayEntered(val newValue: String): Action()
data class BirthdayValidated(val msg: String?): Action()

object SubmitPressed: Action()
data class DataInserted(val id: Long): Action()
data class DataInsertionFailed(val msg: String): Action()
object ToastDisplayed: Action()
