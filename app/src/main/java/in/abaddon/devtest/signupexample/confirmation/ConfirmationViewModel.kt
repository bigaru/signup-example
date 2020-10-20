package `in`.abaddon.devtest.signupexample.confirmation

import `in`.abaddon.devtest.signupexample.R
import `in`.abaddon.devtest.signupexample.UnidirectedViewModel
import `in`.abaddon.devtest.signupexample.model.UserDao
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConfirmationViewModel @Inject constructor(
    val userDao: UserDao
): UnidirectedViewModel<Action,ViewState>(ViewState(), reducer){
    companion object {
        // keep it inside companion object to enforce to be free of side-effects
        private val reducer: (ViewState, Action) -> ViewState = { prevState, action ->
            when(action){
                is LoadUser -> prevState.copy(isLoading = true)
                is UserLoaded -> prevState.copy(
                    isLoading = false,
                    user = action.user,
                    heroText = R.string.hero_text_successful,
                    heroColor = R.color.colorAccent
                )
                is UserLoadFailed -> prevState.copy(
                    isLoading = false,
                    heroText = R.string.hero_text_failure,
                    heroColor = R.color.failure
                )
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
            val maybeUser = userDao.findById(id)

            // simulate any potential delays
            //delay(5000)

            if(maybeUser != null){
                dispatch(UserLoaded(maybeUser))
            } else {
                dispatch(UserLoadFailed)
            }
        }
    }
}
