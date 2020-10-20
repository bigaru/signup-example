package `in`.abaddon.devtest.signupexample.signup

import `in`.abaddon.devtest.signupexample.R
import `in`.abaddon.devtest.signupexample.TextChangedListener
import `in`.abaddon.devtest.signupexample.confirmation.ConfirmationActivity
import `in`.abaddon.devtest.signupexample.databinding.ActivitySignupBinding
import `in`.abaddon.devtest.signupexample.di.ViewModelFactory
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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

        root.submitButton.setOnClickListener { viewModel.dispatch(SubmitPressed) }
        viewModel.viewState.observe(this, {renderEffects(it)})
    }

    private fun renderEffects(viewState: ViewState){
        when(viewState.effect){
            is ShowToast -> {
                val msg = getString(viewState.effect.msgId)
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                viewModel.dispatch(EffectFired)
            }

            is OpenConfirmation -> {
                val intent = Intent(this, ConfirmationActivity::class.java)
                startActivity(intent)
                viewModel.dispatch(EffectFired)
            }
        }
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
