package com.quess.catapp.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.quess.catapp.databinding.FragmentLoginBinding
import com.quess.catapp.ui.cat_list.CatListActivity
import com.quess.catapp.utils.EventObserver
import com.quess.catapp.utils.viewModelProvider
import dagger.android.support.DaggerFragment
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var vm: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vm = viewModelProvider(viewModelFactory)

        val dataBinding = FragmentLoginBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@LoginFragment
            vm = this@LoginFragment.vm
        }

        vm.openCatListFragment.observe(viewLifecycleOwner, EventObserver {
            startActivity(Intent(context, CatListActivity::class.java))
        })

        vm.enableButton(
            dataBinding.emailEditText,
            dataBinding.passwordEditText,
            dataBinding.loginBtn
        )

        return dataBinding.root
    }
}
