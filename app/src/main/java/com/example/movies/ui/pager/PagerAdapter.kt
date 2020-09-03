package com.example.movies.ui.pager


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class PagerAdapter(
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager!!) {

    private var fragments = ArrayList<Fragment>()
    private var titles = ArrayList<String>()

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? = titles[position]

    fun addFragment(fragment: Fragment, title: String) {
        fragments.add(fragment)
        titles.add(title)
    }
}
