package io.github.alvarosanzrodrigo.projectresourcemanager.Adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import io.github.alvarosanzrodrigo.projectresourcemanager.Fragments.CategoriesFragment
import io.github.alvarosanzrodrigo.projectresourcemanager.Fragments.ProjectDocumentsManagerFragment

class PagerAdapter (fm: FragmentManager, internal var mNumOfTabs: Int) : FragmentStatePagerAdapter(fm) {



    override fun getItem(position: Int): Fragment? {
        val documentsFragment: Fragment=
            ProjectDocumentsManagerFragment()
        val categoriesFragment: Fragment = CategoriesFragment()
        return when (position) {
            0 -> {
                documentsFragment
            }
            1 -> {
                categoriesFragment
            }
            2 -> {
                categoriesFragment
            }
            else -> null
        }
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }
}