package `in`.abaddon.devtest.signupexample.confirmation

import `in`.abaddon.devtest.signupexample.UnidirectedViewModel
import `in`.abaddon.devtest.signupexample.model.UserDB
import javax.inject.Inject

class ConfirmationViewModel @Inject constructor(
    val userDB: UserDB
): UnidirectedViewModel<Action,ViewState>(ViewState(), reducer){
    companion object {
        // keep it inside companion object to enforce to be free of side-effects
        private val reducer: (ViewState, Action) -> ViewState = { prevState, action ->
            when(action){
                else -> prevState
            }
        }
    }

    override fun handleActions(action: Action){
        when(action){
        }
    }
}
