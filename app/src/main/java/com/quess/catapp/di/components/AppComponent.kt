package com.quess.catapp.di.components

import com.quess.catapp.Application
import com.quess.catapp.di.modules.ActivityBindingModule
import com.quess.catapp.di.modules.AppModule
import com.quess.catapp.di.modules.NetworkModule
import com.quess.catapp.di.modules.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


/**
 *
 * This is the main injection class that injects [com.quess.catapp.di.components] Activities, and android modules.
 */
@Singleton
@Component(
    modules = [ActivityBindingModule::class,
        AndroidSupportInjectionModule::class,
        ViewModelModule::class,
        NetworkModule::class, AppModule::class]
)
interface AppComponent : AndroidInjector<Application> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<Application>()
}