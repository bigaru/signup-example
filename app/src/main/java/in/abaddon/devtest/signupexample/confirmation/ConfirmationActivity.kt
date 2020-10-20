package `in`.abaddon.devtest.signupexample.confirmation

import `in`.abaddon.devtest.signupexample.R
import `in`.abaddon.devtest.signupexample.databinding.ActivityConfirmationBinding
import `in`.abaddon.devtest.signupexample.di.ViewModelFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
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
        viewModel.dispatch(LoadUser(createdId))

        setTitle(R.string.title_confirmation)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                finish()
                return true;
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
