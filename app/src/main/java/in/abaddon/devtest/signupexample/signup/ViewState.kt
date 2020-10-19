package `in`.abaddon.devtest.signupexample.signup

data class ViewState(
    val name: String,
    val nameError: String?,
    val email: String,
    val emailError: String?,
    val birthday: String,
    val birthdayError: String?,
){
    fun hasError(): Boolean {
        return nameError != null || emailError != null || birthdayError != null
    }

    fun areAllValueSet(): Boolean {
        return name.isNotBlank() && email.isNotBlank() && birthday.isNotBlank()
    }

    fun isSubmitable(): Boolean {
        return !hasError() && areAllValueSet()
    }
}
