package com.quess.catapp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quess.catapp.api.service.CatApiService
import com.quess.catapp.model.BreedImage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val catApiService: CatApiService) : ViewModel() {

    private val compositeDisposableImageBreed = CompositeDisposable()
    val getImageBreeds = MutableLiveData<List<BreedImage>>()
    var isLoadingImages = MutableLiveData<Boolean>()
    var isErrorImages = MutableLiveData<Boolean>()

    fun loadBreedImages(
        breedId: String?,
        imageLimit: Int,
        size: String?,
        memeTypes: String?
    ) {
        val subsBreedImages = catApiService.getCatImages(breedId!!, imageLimit, size!!, memeTypes)

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                isLoadingImages.value = true
                Timber.d("Loading Image Detail")
            }
            .doOnTerminate {
                isLoadingImages.value = false
                Timber.d("Finish Image Detail")
            }
            .subscribe({
                this.getImageBreeds.postValue(it)
            })
            {
                isErrorImages.value = true
                Timber.e(it)
            }
        compositeDisposableImageBreed.add(subsBreedImages)
    }
}