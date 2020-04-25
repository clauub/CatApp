package com.quess.catapp.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideContext(context: com.quess.catapp.Application): Context {
        return context.applicationContext
    }

    @Singleton
    @Provides
    fun provideApplication(application: Application): Application {
        return application
    }
}
