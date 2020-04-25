package com.quess.catapp.utils

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

private fun getFragmentManager(context: Context): FragmentManager {
    return (context as AppCompatActivity).supportFragmentManager
}

fun replaceFragment(context: Context, frameId: Int, fragment: Fragment) {
    getFragmentManager(context).beginTransaction()
        .replace(frameId, fragment)
        .addToBackStack(null).commit()
}

fun replaceFragmentBundle(context: Context, frameId: Int, fragment: Fragment, bundle: Bundle) {
    fragment.arguments = bundle
    getFragmentManager(context).beginTransaction()
        .replace(frameId, fragment)
        .addToBackStack(null).commit()
}
