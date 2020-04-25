package com.quess.catapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.quess.catapp.R
import com.quess.catapp.databinding.FragmentCatListBinding
import com.quess.catapp.databinding.FragmentDetailBinding
import com.quess.catapp.model.Breed
import com.quess.catapp.model.BreedImage
import com.quess.catapp.ui.cat_list.CatListViewModel
import com.quess.catapp.ui.detail.DetailActivity.Companion.BREED
import com.quess.catapp.ui.detail.DetailActivity.Companion.BREED_IMAGE
import com.quess.catapp.utils.Constants
import com.quess.catapp.utils.viewModelProvider
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
@Suppress("UNREACHABLE_CODE")
class DetailFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var vm: DetailViewModel

    private var images: List<BreedImage>? = null

    lateinit var dataBinding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vm = viewModelProvider(viewModelFactory)

        dataBinding = FragmentDetailBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@DetailFragment
            vm = this@DetailFragment.vm
        }

        val selectedBreed = activity!!.intent.extras!!.getParcelable<Breed>(BREED)

        initCatListImages()

        vm.loadBreedImages(
            selectedBreed!!.id,
            Constants.IMAGES_LIMIT,
            Constants.SIZE_FULL,
            Constants.JPG
        )

        dataBinding.breed = selectedBreed

        Timber.d("BREED-DETAIL : ${selectedBreed.name} + ${selectedBreed.id}")
        return dataBinding.root
    }

    private fun initCatListImages() {

        vm.getImageBreeds.observe(viewLifecycleOwner, Observer {
            if (it != null) {

                images = it

                for (image in images!!) {
                    dataBinding.imageUrl = image.url
                }
            }
        })
    }

}
