package com.quess.catapp.di.modules

import com.quess.catapp.ui.cat_list.CatListActivity
import com.quess.catapp.ui.cat_list.CatListModule
import com.quess.catapp.ui.detail.DetailActivity
import com.quess.catapp.ui.detail.DetailModule
import com.quess.catapp.ui.login.LoginActivity
import com.quess.catapp.ui.login.LoginModule
import com.quess.thing.di.scopes.ActivityScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [CatListModule::class]
    )
    internal abstract fun catListActivity(): CatListActivity

    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [LoginModule::class]
    )
    internal abstract fun loginActivity(): LoginActivity

    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [DetailModule::class]
    )
    internal abstract fun detailActivity(): DetailActivity

}
