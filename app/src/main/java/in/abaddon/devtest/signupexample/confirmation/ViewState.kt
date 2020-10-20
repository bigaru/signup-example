package `in`.abaddon.devtest.signupexample.confirmation

import `in`.abaddon.devtest.signupexample.model.User
import androidx.annotation.ColorRes
import androidx.annotation.StringRes

data class ViewState(
               val isLoading: Boolean = false,
               val user: User? = null,
    @StringRes val heroText: Int? = null,
    @ColorRes  val heroColor: Int? = null
)
