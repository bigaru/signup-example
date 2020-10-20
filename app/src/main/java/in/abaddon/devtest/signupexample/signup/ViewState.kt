package `in`.abaddon.devtest.signupexample.signup

sealed class Effect()
data class ShowToast(val msg: String): Effect()

data class ViewState(
    val name: String = "",
    val nameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val birthday: String = "",
    val birthdayError: String? = null,
    val isLoading: Boolean = false,
    val effect: Effect? = null
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
