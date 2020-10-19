package `in`.abaddon.devtest.signupexample.signup

import `in`.abaddon.devtest.signupexample.R
import `in`.abaddon.devtest.signupexample.TextChangedListener
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
    lateinit var root: ActivitySignupBinding
    lateinit var viewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        root = DataBindingUtil.setContentView(this, R.layout.activity_signup)

        viewModel = ViewModelProvider(this, viewModelFactory).get(SignupViewModel::class.java)
        initEdittexts()

        root.vm = viewModel
        root.lifecycleOwner = this
    }

    private fun initEdittexts(){
        root.fieldEmail.addTextChangedListener(
            TextChangedListener{viewModel.dispatch(EmailEntered(it))}
        )

        root.fieldName.addTextChangedListener(
            TextChangedListener{viewModel.dispatch(NameEntered(it))}
        )

        root.fieldBirthday.addTextChangedListener(
            TextChangedListener{viewModel.dispatch(BirthdayEntered(it))}
        )
    }
}
