package `in`.abaddon.devtest.signupexample.confirmation

import `in`.abaddon.devtest.signupexample.model.User

sealed class Action()
data class LoadUser(val id:Long): Action()
data class UserLoaded(val user: User): Action()
object UserLoadFailed: Action()
