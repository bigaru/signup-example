package `in`.abaddon.devtest.signupexample.signup

import `in`.abaddon.devtest.signupexample.Validator
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SignupViewModel @Inject constructor(val validator: Validator): ViewModel(){
    companion object {
        private val initialState = ViewState("", null, "", null, "", null)

        private val reducer: (ViewState, Action) -> ViewState = { prevState, action ->
            when(action){
                is NameEntered -> prevState.copy(name = action.newValue)
                is EmailEntered -> prevState.copy(email = action.newValue)
                is BirthdayEntered -> prevState.copy(birthday = action.newValue)

                is NameValidated -> prevState.copy(nameError = action.msg)
                is EmailValidated -> prevState.copy(emailError = action.msg)
                is BirthdayValidated -> prevState.copy(birthdayError = action.msg)
            }
        }
    }

    private val _action: MutableLiveData<Action> = MutableLiveData()
    private val _viewState: MutableLiveData<ViewState> = MutableLiveData(initialState)
    val viewState: LiveData<ViewState> = _viewState

    init {
        _action.observeForever{
            reduce(it)
            handleActions(it)
        }
    }

    fun dispatch(action: Action){
        _action.postValue(action)
    }

    private fun reduce(action: Action){
        val newState = reducer(_viewState.value!!, action)
        _viewState.postValue(newState)
    }

    private fun handleActions(action: Action){
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
        }

    }
}
