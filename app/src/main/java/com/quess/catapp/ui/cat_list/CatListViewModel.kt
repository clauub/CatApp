package com.quess.catapp.ui.cat_list

import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quess.catapp.api.service.CatApiService
import com.quess.catapp.model.Breed
import com.quess.catapp.model.BreedImage
import com.quess.catapp.utils.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class CatListViewModel @Inject constructor(
    private val catApiService: CatApiService,
    private val context: Context
) : ViewModel() {

    private val compositeDisposableBreed = CompositeDisposable()
    private val compositeDisposableImageBreed = CompositeDisposable()
    val getBreeds = MutableLiveData<List<Breed>>()
    val getImageBreeds = MutableLiveData<List<BreedImage>>()
    var isLoading = MutableLiveData<Boolean>()
    var isLoadingImages = MutableLiveData<Boolean>()
    var isError = MutableLiveData<Boolean>()
    var isErrorImages = MutableLiveData<Boolean>()

    var isAnimation = ObservableBoolean(false)

    private val _onCardViewClicked = MutableLiveData<Event<Unit>>()
    val onCardViewClicked: LiveData<Event<Unit>>
        get() = _onCardViewClicked

    private val _onRetryBtnClicked = MutableLiveData<Event<Unit>>()
    val onRetryBtnClicked: LiveData<Event<Unit>>
        get() = _onRetryBtnClicked

    fun onClickCardView() {
        _onCardViewClicked.value = Event(Unit)
    }

    fun onRetryBtnClicked() {
        _onRetryBtnClicked.value = Event(Unit)
    }

    fun loadCatBreeds() {
        val subs = catApiService.getCatsBreeds()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                isLoading.value = true
                Timber.d("Loading")
            }
            .doOnTerminate {
                isLoading.value = false
                Timber.d("Finish")
            }
            .subscribe({
                this.getBreeds.postValue(it)
            })
            {
                isError.value = true
                Timber.e(it)
            }
        compositeDisposableBreed.add(subs)
    }

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
                Timber.d("Loading Cat Breed Images")
            }
            .doOnTerminate {
                isLoadingImages.value = false
                Timber.d("Finish Cat Breed Images")
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