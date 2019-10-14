package org.agh.pracinz.evog.view.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class TabAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val fragments = mutableListOf<Fragment>()
    private val titles = mutableListOf<String>()


    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }


    fun addTab(fragment: Fragment, title: String) {
        titles.add(title)
        fragments.add(fragment)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}