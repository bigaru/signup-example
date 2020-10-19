package `in`.abaddon.devtest.signupexample

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("app:error")
fun setError(layout: TextInputLayout, text: String?) {
    layout.error = text
}
