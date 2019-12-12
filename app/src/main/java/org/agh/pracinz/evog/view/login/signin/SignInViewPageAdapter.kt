package org.agh.pracinz.evog.view.login.signin

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class SignInViewPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm){


    private val NUMBER_OF_PAGES = 3

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> AccountFragment()
            1 -> PersonalInfoFragment()
            2 -> ContactInfoFragment()
            else -> throw RuntimeException("Can`t find fragment")
        }
    }

    override fun getCount(): Int {
        return NUMBER_OF_PAGES
    }

}