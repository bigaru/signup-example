package `in`.abaddon.devtest.signupexample

import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("app:error")
fun setError(layout: TextInputLayout, @StringRes textId: Int?) {
    val text = textId?.let { layout.context.getString(it) }
    layout.error = text
}

@BindingAdapter("app:nullableText")
fun setNullableText(textView: TextView, @StringRes textId: Int?) {
    val text = textId?.let { textView.context.getString(it) }
    textView.text = text
}

@BindingAdapter("app:nullableBackground")
fun setNullableBackground(view: View, @ColorRes colorId: Int?) {
    val ctx = view.context
    val color = colorId?.let { ctx.getColor(it) } ?: ctx.getColor(android.R.color.transparent)
    view.setBackgroundColor(color)
}
