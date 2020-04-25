package com.quess.catapp.ui.cat_list

import androidx.lifecycle.ViewModel
import com.quess.thing.di.scopes.FragmentScoped
import com.quess.thing.di.scopes.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class CatListModule {

    /**
     * Generates an [AndroidInjector] for the [CatListFragment].
     */
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeCatListFragment(): CatListFragment


    /**
     * The ViewModels are created by Dagger in a map. Via the @ViewModelKey, we define that we
     * want to get a [CatListViewModel] class.
     */
    @Binds
    @IntoMap
    @ViewModelKey(CatListViewModel::class)
    abstract fun bindCatListViewModel(viewModel: CatListViewModel): ViewModel


}