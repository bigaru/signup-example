package `in`.abaddon.devtest.signupexample.confirmation

import `in`.abaddon.devtest.signupexample.UnidirectedViewModel
import `in`.abaddon.devtest.signupexample.model.UserDB
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConfirmationViewModel @Inject constructor(
    val userDB: UserDB
): UnidirectedViewModel<Action,ViewState>(ViewState(), reducer){
    companion object {
        // keep it inside companion object to enforce to be free of side-effects
        private val reducer: (ViewState, Action) -> ViewState = { prevState, action ->
            when(action){
                is LoadUser -> prevState.copy(isLoading = true)
                is UserLoaded -> prevState.copy(isLoading = false, user = action.user)
            }
        }
    }

    override fun handleActions(action: Action){
        when(action){
            is LoadUser -> fetchUser(action.id)
        }
    }

    private fun fetchUser(id: Long){
        viewModelScope.launch(Dispatchers.IO) {
            val maybeUser = userDB.userDao().findById(id)

            // simulate any potential delays
            //delay(5000)

            if(maybeUser != null){
                dispatch(UserLoaded(maybeUser))
            } else {
                // TODO handle not found case
            }
        }
    }
}
