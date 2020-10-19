package `in`.abaddon.devtest.signupexample

import android.text.Editable
import android.text.TextWatcher

class TextChangedListener(val onChanged: (String)->Unit): TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(editable: Editable?) {
        val value = editable?.toString() ?: ""
        onChanged(value)
    }

}
