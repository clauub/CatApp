package com.quess.catapp.ui.cat_list

import android.os.Bundle
import com.quess.catapp.R
import com.quess.catapp.utils.inTransaction
import dagger.android.support.DaggerAppCompatActivity


class CatListActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat_list)

        if (savedInstanceState == null) {
            supportFragmentManager.inTransaction {
                add(R.id.container, CatListFragment())
            }
        }
    }
}
