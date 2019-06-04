package io.github.alvarosanzrodrigo.projectresourcemanager.adapters

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import io.github.alvarosanzrodrigo.projectresourcemanager.fragments.CategoriesFragment
import io.github.alvarosanzrodrigo.projectresourcemanager.fragments.ProjectDocumentsManagerFragment

class PagerAdapter (fm: FragmentManager, internal var mNumOfTabs: Int, internal var projectId: Int, internal var projectName: String) : FragmentStatePagerAdapter(fm) {



    override fun getItem(position: Int): Fragment? {
        val bundle = Bundle()
        bundle.putString("projectName", projectName)
        bundle.putInt("projectId", projectId)
        val documentsFragment: Fragment=
            ProjectDocumentsManagerFragment()
        documentsFragment.arguments = bundle



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