package `in`.abaddon.devtest.signupexample.signup

data class ViewState(
    val name: String,
    val nameError: String?,
    val email: String,
    val emailError: String?,
    val birthday: String,
    val birthdayError: String?,
){}
