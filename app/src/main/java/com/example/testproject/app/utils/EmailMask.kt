package com.example.testproject.app.utils

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputLayout

/**
 * @author Nedumayy (Samim)
 * Mask to display the email in the format: test@example.com
 * Use this class to display the email
 * Error message will be displayed if the email is invalid or empty on TextInputLayout
 */
class EmailMask(private val tilEmail: TextInputLayout) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        val valid =
            android.util.Patterns.EMAIL_ADDRESS.matcher(s?.trim().toString()).matches()
        if (!valid) {
            tilEmail.error = INVALID_ADDRESS
        } else {
            tilEmail.error = EMPTY_FIELD
        }
    }

    override fun afterTextChanged(s: Editable?) {
        if (s?.length == 0) {
            tilEmail.error = NOT_BE_EMPTY
        }
    }

    companion object {

        private const val EMPTY_FIELD = ""
        private const val INVALID_ADDRESS = "Invalid Email address"
        private const val NOT_BE_EMPTY = "Required Field!"
    }
}