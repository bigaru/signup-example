package `in`.abaddon.devtest.signupexample.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SignupViewModel @Inject constructor(): ViewModel(){
    companion object {
        private val initialState = ViewState("", null, "", null, "", null)

        private val reduce: (ViewState, Action) -> ViewState = { prevState, action ->
            when(action){
                is NameEntered -> prevState.copy(name = action.newValue)
                is EmailEntered -> prevState.copy(email = action.newValue)
                is BirthdayEntered -> prevState.copy(birthday = action.newValue)
            }
        }
    }

    private val _action: MutableLiveData<Action> = MutableLiveData()
    private val _viewState: MutableLiveData<ViewState> = MutableLiveData(initialState)
    val viewState: LiveData<ViewState> = _viewState

    init {
        _action.observeForever{
            val newState = reduce(_viewState.value!!, it)
            _viewState.postValue(newState)

            //TODO add Handler
        }
    }

    fun dispatch(action: Action){
        _action.postValue(action)
    }
}
