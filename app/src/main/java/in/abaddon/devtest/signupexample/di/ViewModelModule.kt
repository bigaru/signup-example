package `in`.abaddon.devtest.signupexample.di

import `in`.abaddon.devtest.signupexample.confirmation.ConfirmationViewModel
import `in`.abaddon.devtest.signupexample.signup.SignupViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule{
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SignupViewModel::class)
    fun bindSignupViewModel(vm: SignupViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ConfirmationViewModel::class)
    fun bindConfirmationViewModel(vm: ConfirmationViewModel): ViewModel
}
