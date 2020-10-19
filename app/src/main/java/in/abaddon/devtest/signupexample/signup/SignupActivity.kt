package `in`.abaddon.devtest.signupexample.signup

import `in`.abaddon.devtest.signupexample.R
import `in`.abaddon.devtest.signupexample.databinding.ActivitySignupBinding
import `in`.abaddon.devtest.signupexample.di.ViewModelFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import javax.inject.Inject

class SignupActivity : AppCompatActivity() {
    @Inject lateinit var viewModelFactory: ViewModelFactory
    lateinit var signupBinding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        signupBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(SignupViewModel::class.java)

        // TODO use BindingAdapter instead
        viewModel.viewState.observe(this){render(it)}
    }

    private fun render(viewState: ViewState) {
    }
}
