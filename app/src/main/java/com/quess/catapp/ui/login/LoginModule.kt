package com.quess.catapp.ui.login

import androidx.lifecycle.ViewModel
import com.quess.thing.di.scopes.FragmentScoped
import com.quess.thing.di.scopes.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap


@Module
internal abstract class LoginModule {

    /**
     * Generates an [AndroidInjector] for the [LoginFragment].
     */
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeLoginFragment(): LoginFragment


    /**
     * The ViewModels are created by Dagger in a map. Via the @ViewModelKey, we define that we
     * want to get a [LoginViewModel] class.
     */
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel


}