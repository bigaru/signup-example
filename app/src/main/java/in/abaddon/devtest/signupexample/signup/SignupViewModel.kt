package `in`.abaddon.devtest.signupexample.signup

import `in`.abaddon.devtest.signupexample.Validator
import `in`.abaddon.devtest.signupexample.model.User
import `in`.abaddon.devtest.signupexample.model.UserDB
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignupViewModel @Inject constructor(
    val validator: Validator,
    val userDB: UserDB
): ViewModel(){
    companion object {
        // keep it inside companion object to enforce to be free of side-effects
        private val reducer: (ViewState, Action) -> ViewState = { prevState, action ->
            when(action){
                is NameEntered -> prevState.copy(name = action.newValue)
                is EmailEntered -> prevState.copy(email = action.newValue)
                is BirthdayEntered -> prevState.copy(birthday = action.newValue)

                is NameValidated -> prevState.copy(nameError = action.msg)
                is EmailValidated -> prevState.copy(emailError = action.msg)
                is BirthdayValidated -> prevState.copy(birthdayError = action.msg)

                is SubmitPressed -> prevState.copy(isLoading = true)
                is DataInserted -> prevState.copy(effect = ShowToast("User inserted: ${action.id}"), isLoading = false)
                is DataInsertionFailed -> prevState.copy(effect = ShowToast(action.msg), isLoading = false)
                is ToastDisplayed -> prevState.copy(effect = null)
            }
        }
    }

    private val _action: MutableLiveData<Action> = MutableLiveData()
    private val _viewState: MutableLiveData<ViewState> = MutableLiveData(ViewState())
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

            val dao = userDB.userDao()

            // simulate any potential delays
            //delay(5000)

            try {
                val id = dao.insert(user)
                _action.postValue(DataInserted(id))
            } catch (e: SQLiteConstraintException){
                _action.postValue(DataInsertionFailed("Unfortunately this email already exists."))
            }
        }
    }
}
