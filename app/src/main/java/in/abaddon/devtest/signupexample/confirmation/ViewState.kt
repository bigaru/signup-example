package `in`.abaddon.devtest.signupexample.confirmation

import androidx.annotation.StringRes

sealed class Effect()

data class ViewState(val name: String         = "", )
