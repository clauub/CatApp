package com.quess.catapp.ui.login

import android.os.Bundle
import com.quess.catapp.R
import com.quess.catapp.utils.inTransaction
import dagger.android.support.DaggerAppCompatActivity

class LoginActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (savedInstanceState == null) {
            supportFragmentManager.inTransaction {
                add(R.id.container, LoginFragment())
            }
        }
    }
}
