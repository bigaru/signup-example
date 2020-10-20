package `in`.abaddon.devtest.signupexample.signup

import androidx.annotation.StringRes

sealed class Effect()
data class ShowToast(@StringRes val msgId: Int): Effect()

data class ViewState(
               val name: String         = "",
    @StringRes val nameError: Int?      = null,
               val email: String        = "",
    @StringRes val emailError: Int?     = null,
               val birthday: String     = "",
    @StringRes val birthdayError: Int?  = null,
               val isLoading: Boolean   = false,
               val effect: Effect?      = null
){
    fun hasError(): Boolean {
        return nameError != null || emailError != null || birthdayError != null
    }

    fun areAllValueSet(): Boolean {
        return name.isNotBlank() && email.isNotBlank() && birthday.isNotBlank()
    }

    fun isSubmitable(): Boolean {
        return !hasError() && areAllValueSet() && !isLoading
    }
}
