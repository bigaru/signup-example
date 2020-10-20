package `in`.abaddon.devtest.signupexample.confirmation

import `in`.abaddon.devtest.signupexample.R
import `in`.abaddon.devtest.signupexample.databinding.ActivityConfirmationBinding
import `in`.abaddon.devtest.signupexample.di.ViewModelFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import javax.inject.Inject

class ConfirmationActivity : AppCompatActivity() {
    companion object {
        const val NEWLY_CREATED_ID = "NEWLY_CREATED_ID"
    }

    @Inject lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        val root: ActivityConfirmationBinding = DataBindingUtil.setContentView(this, R.layout.activity_confirmation)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ConfirmationViewModel::class.java)

        root.vm = viewModel
        root.lifecycleOwner = this

        val createdId = intent.getLongExtra(NEWLY_CREATED_ID, -1)
        if(createdId != -1L){
            viewModel.dispatch(LoadUser(createdId))
        }
    }
}
