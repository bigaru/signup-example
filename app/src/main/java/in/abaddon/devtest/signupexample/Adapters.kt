package `in`.abaddon.devtest.signupexample

import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("app:error")
fun setError(layout: TextInputLayout, @StringRes textId: Int?) {
    val text = textId?.let { layout.context.getString(it) }
    layout.error = text
}
