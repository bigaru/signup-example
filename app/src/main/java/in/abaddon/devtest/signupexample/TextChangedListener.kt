package `in`.abaddon.devtest.signupexample

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher

class TextChangedListener(val onChanged: (String)->Unit): TextWatcher {
    private val handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable = Runnable {  }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        handler.removeCallbacks(runnable)
    }

    override fun afterTextChanged(editable: Editable?) {
        runnable = Runnable {
            val value = editable?.toString() ?: ""
            onChanged(value)
        }

        handler.postDelayed(runnable, 900)
    }

}
