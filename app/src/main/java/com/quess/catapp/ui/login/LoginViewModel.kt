package com.quess.catapp.ui.login

import android.annotation.SuppressLint
import android.util.Patterns
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent
import com.quess.catapp.utils.Event
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LoginViewModel @Inject constructor() : ViewModel() {

    private val _openCatListFragment = MutableLiveData<Event<Unit>>()
    val openCatListFragment: LiveData<Event<Unit>>
        get() = _openCatListFragment

    fun onLoginBtnClicked() {
        _openCatListFragment.value = Event(Unit)
    }

//    Button disabled until email and password complete if condition
    @SuppressLint("CheckResult")
    fun enableButton(
        editTextEmail: TextInputEditText,
        editTextPassword: TextInputEditText,
        loginBtn: MaterialButton
    ) {
        val emailChangeObservable = RxTextView.textChangeEvents(editTextEmail)
        val passwordChangeObservable = RxTextView.textChangeEvents(editTextPassword)

        Observable.combineLatest(emailChangeObservable,
            passwordChangeObservable,
            BiFunction { emailObservable: TextViewTextChangeEvent, passwordObservable: TextViewTextChangeEvent ->
                val emailCheck =
                    emailObservable.text().length >= 6 && Patterns.EMAIL_ADDRESS.matcher(
                        emailObservable.text()
                    ).matches()
                val passwordCheck = passwordObservable.text().length >= 6
                emailCheck && passwordCheck
            }
        ).subscribe { aBoolean ->
            loginBtn.isEnabled = aBoolean
        }
    }

}