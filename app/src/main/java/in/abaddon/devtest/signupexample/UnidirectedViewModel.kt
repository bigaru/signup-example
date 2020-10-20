package `in`.abaddon.devtest.signupexample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class UnidirectedViewModel<Action, ViewState>(
    initialState: ViewState,
    private val reducer: (ViewState, Action) -> ViewState
): ViewModel() {

    private val _action: MutableLiveData<Action> = MutableLiveData()
    private val _viewState: MutableLiveData<ViewState> = MutableLiveData(initialState)
    val viewState: LiveData<ViewState> = _viewState

    init {
        _action.observeForever{
            reduce(it)
            handleActions(it)
        }
    }

    private fun reduce(action: Action){
        val newState = reducer(_viewState.value!!, action)
        _viewState.postValue(newState)
    }

    abstract protected fun handleActions(action: Action)

    fun dispatch(action: Action){
        _action.postValue(action)
    }
}
