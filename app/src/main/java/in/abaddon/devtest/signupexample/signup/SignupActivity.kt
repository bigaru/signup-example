package `in`.abaddon.devtest.signupexample.signup

import `in`.abaddon.devtest.signupexample.R
import `in`.abaddon.devtest.signupexample.databinding.ActivitySignupBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.android.AndroidInjection

class SignupActivity : AppCompatActivity() {
    lateinit var signupBinding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        signupBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup)

        // TODO inject via Dagger
        val viewModel = SignupViewModel()

        // TODO use BindingAdapter instead
        viewModel.viewState.observe(this){render(it)}
    }

    private fun render(viewState: ViewState) {
    }
}
