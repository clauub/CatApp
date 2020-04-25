package com.quess.catapp.ui.cat_list

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.style.TtsSpan
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.quess.catapp.R
import com.quess.catapp.databinding.FragmentCatListBinding
import com.quess.catapp.model.Breed
import com.quess.catapp.model.BreedImage
import com.quess.catapp.ui.detail.DetailActivity
import com.quess.catapp.ui.detail.DetailActivity.Companion.BREED
import com.quess.catapp.ui.detail.DetailActivity.Companion.BREED_IMAGE
import com.quess.catapp.utils.Constants.IMAGES_LIMIT
import com.quess.catapp.utils.Constants.JPG
import com.quess.catapp.utils.Constants.SIZE_MED
import com.quess.catapp.utils.EventObserver
import com.quess.catapp.utils.viewModelProvider
import dagger.android.support.DaggerFragment
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject


@Suppress("UNREACHABLE_CODE")
class CatListFragment : DaggerFragment(), PopupMenu.OnMenuItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var vm: CatListViewModel

    lateinit var dataBinding: FragmentCatListBinding

    private var spinnerBreedList: MutableList<String?>? = null
    private var breeds: List<Breed>? = null
    private var selectedBreed: Breed? = null
    private var images: List<BreedImage>? = null
    private var selectedImages: BreedImage? = null
    private var adapter: ArrayAdapter<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vm = viewModelProvider(viewModelFactory)

        dataBinding = FragmentCatListBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@CatListFragment
            vm = this@CatListFragment.vm
        }

        spinnerBreedList = ArrayList()

        vm.loadCatBreeds()

        initCatListForSpinner()

        initSpinnerAdapter()

        initSpinnerOnItemClicked()

        initCatListImages()

        showPopup()

        vm.onCardViewClicked.observe(viewLifecycleOwner, EventObserver {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(BREED, selectedBreed)
            intent.putExtra(BREED_IMAGE, selectedImages)
            startActivity(intent)
        })

        vm.onRetryBtnClicked.observe(viewLifecycleOwner, EventObserver {
            vm.loadCatBreeds()
        })

        return dataBinding.root
    }

    private fun showPopup() {
        dataBinding.iconSortLines.setOnClickListener {
            val popup = PopupMenu(context!!, it)
            popup.setOnMenuItemClickListener(this)
            popup.inflate(R.menu.menu_sort_list)
            try {
                val fieldPopupMenu = PopupMenu::class.java.getDeclaredField("mPopup")
                fieldPopupMenu.isAccessible = true
                val mPopup = fieldPopupMenu.get(popup)
                mPopup.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(mPopup, true)
            } catch (e: Exception) {
                Timber.d("Error icons : $e")
            }
            popup.show()
        }
    }

    private fun initSpinnerAdapter() {
        adapter = object : ArrayAdapter<String>(
            activity!!.applicationContext,
            R.layout.spinner_item,
            spinnerBreedList!!
        ) {
            override fun isEnabled(position: Int): Boolean {
                // disable the 1st item, as it is used as a hint
                return position != 0
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view =
                    super.getDropDownView(position, convertView, parent)
                val dropdownItem = view as TextView
                dropdownItem.setTextColor(if (position == 0) Color.GRAY else Color.WHITE)
                return view
            }
        }
        adapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dataBinding.breedsSpinner.adapter = adapter
    }

    private fun initSpinnerOnItemClicked() {
        dataBinding.breedsSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                    for (breed in breeds!!) {
                        if (breed.name!! == p0?.getItemAtPosition(p2)) {
                            selectedBreed = breed
                            break
                        }
                    }

                    if (selectedBreed == null) return

                    vm.loadBreedImages(selectedBreed!!.id, IMAGES_LIMIT, SIZE_MED, JPG)

                    dataBinding.breed = selectedBreed

                    dataBinding.cardViewInfo.visibility = View.VISIBLE
                }

            }
    }

    private fun initCatListForSpinner() {
        vm.getBreeds.observe(viewLifecycleOwner, Observer {
            if (it != null) {

                breeds = it

                spinnerBreedList!!.clear()

                for (breed in breeds!!) {
                    spinnerBreedList!!.add(breed.name)
                }

                spinnerBreedList!!.add(0, getString(R.string.search_for_your_breed))
                adapter!!.notifyDataSetChanged()

            }
        })
    }

    private fun initCatListFilteredByCountry() {
        vm.getBreeds.observe(viewLifecycleOwner, Observer { it ->
            if (it != null) {

                breeds = it

                spinnerBreedList!!.clear()

                val sortedList = breeds!!.sortedWith(compareBy { it.origin })

                for (breed in sortedList) {
                    spinnerBreedList!!.add(breed.name)
                }

                spinnerBreedList!!.add(0, getString(R.string.search_breed_sorted_country))
                adapter!!.notifyDataSetChanged()

            }
        })
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

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            R.id.menu_sort_name -> {
                initCatListForSpinner()
                true
            }
            R.id.menu_sort_country -> {
                initCatListFilteredByCountry()
                true
            }
            else -> false
        }
    }

}
