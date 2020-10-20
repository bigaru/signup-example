package `in`.abaddon.devtest.signupexample.signup

import androidx.annotation.StringRes

sealed class Action()
data class NameEntered(val newValue: String): Action()
data class NameValidated(@StringRes val msgId: Int?): Action()

data class EmailEntered(val newValue: String): Action()
data class EmailValidated(@StringRes val msgId: Int?): Action()

data class BirthdayEntered(val newValue: String): Action()
data class BirthdayValidated(@StringRes val msgId: Int?): Action()

object SubmitPressed: Action()
data class DataInserted(val id: Long): Action()
data class DataInsertionFailed(@StringRes val msgId: Int): Action()

object EffectFired: Action()
