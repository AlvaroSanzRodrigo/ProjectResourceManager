package io.github.alvarosanzrodrigo.projectresourcemanager

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class PagerAdapter (fm: FragmentManager, internal var mNumOfTabs: Int) : FragmentStatePagerAdapter(fm) {



    override fun getItem(position: Int): Fragment? {
        val tabFragment: Fragment= ProjectDocumentsManagerFragment()
        return when (position) {
            0 -> {
                tabFragment
            }
            1 -> {
                tabFragment
            }
            2 -> {
                tabFragment
            }
            else -> null
        }
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }
}