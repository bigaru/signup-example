package `in`.abaddon.devtest.signupexample.signup

import `in`.abaddon.devtest.signupexample.R
import `in`.abaddon.devtest.signupexample.UnidirectedViewModel
import `in`.abaddon.devtest.signupexample.Validator
import `in`.abaddon.devtest.signupexample.model.User
import `in`.abaddon.devtest.signupexample.model.UserDao
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignupViewModel @Inject constructor(
    val validator: Validator,
    val userDao: UserDao
): UnidirectedViewModel<Action,ViewState>(ViewState(),reducer){
    companion object {
        // keep it inside companion object to enforce to be free of side-effects
        private val reducer: (ViewState, Action) -> ViewState = { prevState, action ->
            when(action){
                is NameEntered -> prevState.copy(name = action.newValue)
                is EmailEntered -> prevState.copy(email = action.newValue)
                is BirthdayEntered -> prevState.copy(birthday = action.newValue)

                is NameValidated -> prevState.copy(nameError = action.msgId)
                is EmailValidated -> prevState.copy(emailError = action.msgId)
                is BirthdayValidated -> prevState.copy(birthdayError = action.msgId)

                is SubmitPressed -> prevState.copy(isLoading = true)
                is DataInserted -> prevState.copy(effect = OpenConfirmation(action.id), isLoading = false)
                is DataInsertionFailed -> prevState.copy(effect = ShowToast(action.msgId), isLoading = false)
                is EffectFired -> prevState.copy(effect = null)
            }
        }
    }

    override fun handleActions(action: Action){
        when(action){
            is NameEntered -> {
                val maybeMsg = validator.validateName(action.newValue)
                dispatch(NameValidated(maybeMsg))
            }

            is EmailEntered -> {
                val maybeMsg = validator.validateEmail(action.newValue)
                dispatch(EmailValidated(maybeMsg))
            }

            is BirthdayEntered -> {
                val maybeMsg = validator.validateBirthday(action.newValue)
                dispatch(BirthdayValidated(maybeMsg))
            }

            is SubmitPressed -> {
                if(viewState.value!!.isSubmitable()){
                    persistData()
                }
            }
        }
    }

    private fun persistData(){
        viewModelScope.launch(Dispatchers.IO) {
            val state = viewState.value!!
            val user = User(0, state.name, state.email, state.birthday)

            // simulate any potential delays
            //delay(5000)

            try {
                val id = userDao.insert(user)
                dispatch(DataInserted(id))
            } catch (e: SQLiteConstraintException){
                dispatch(DataInsertionFailed(R.string.msg_submission_failed))
            }
        }
    }
}
