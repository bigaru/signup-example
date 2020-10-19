package `in`.abaddon.devtest.signupexample.signup

sealed class Action()
data class NameEntered(val newValue: String): Action()
data class EmailEntered(val newValue: String): Action()
data class BirthdayEntered(val newValue: String): Action()
