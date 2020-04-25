package com.quess.catapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.quess.catapp.R
import com.quess.catapp.ui.cat_list.CatListFragment
import com.quess.catapp.utils.inTransaction
import dagger.android.support.DaggerAppCompatActivity

class DetailActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if (savedInstanceState == null) {
            supportFragmentManager.inTransaction {
                add(R.id.container, DetailFragment())
            }
        }
    }

    companion object {
        const val BREED_IMAGE = "breed_image"
        const val BREED = "breed"
    }
}
