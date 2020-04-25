package com.quess.catapp.ui.detail

import androidx.lifecycle.ViewModel
import com.quess.catapp.model.Breed
import com.quess.catapp.model.BreedImage
import com.quess.catapp.ui.detail.DetailActivity.Companion.BREED
import com.quess.catapp.ui.detail.DetailActivity.Companion.BREED_IMAGE
import com.quess.thing.di.scopes.ActivityScoped
import com.quess.thing.di.scopes.FragmentScoped
import com.quess.thing.di.scopes.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class DetailModule {

    /**
     * Generates an [AndroidInjector] for the [DetailFragment].
     */
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeDetailFragment(): DetailFragment


    /**
     * The ViewModels are created by Dagger in a map. Via the @ViewModelKey, we define that we
     * want to get a [DetailViewModel] class.
     */
    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindDetailViewModel(viewModel: DetailViewModel): ViewModel

    @Module
    companion object {
        @Provides
        @ActivityScoped
        @JvmStatic
        internal fun provideBreedImage(activity: DetailActivity): BreedImage? =
            activity.intent.extras!!.getParcelable(BREED_IMAGE)

        @Provides
        @ActivityScoped
        @JvmStatic
        internal fun provideBreed(activity: DetailActivity): Breed? =
            activity.intent.extras!!.getParcelable(BREED)
    }
}