package `in`.abaddon.devtest.signupexample.confirmation

import `in`.abaddon.devtest.signupexample.model.UserDB
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class ConfirmationViewModel @Inject constructor(
    val userDB: UserDB
): ViewModel(){
    companion object {
        // keep it inside companion object to enforce to be free of side-effects
        private val reducer: (ViewState, Action) -> ViewState = { prevState, action ->
            when(action){
                else -> prevState
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
        }
    }
}
