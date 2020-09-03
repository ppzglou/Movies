package com.example.movies.ui.pager

import android.os.Bundle
import android.view.View
import com.example.movies.base.BaseFragment
import com.example.movies.databinding.FragmentPagerBinding
import com.example.movies.ui.mylist.MyListFragment
import com.example.movies.ui.home.HomeFragment


class PagerFragment : BaseFragment<FragmentPagerBinding>() {

    override fun getViewBinding(): FragmentPagerBinding =
        FragmentPagerBinding.inflate(layoutInflater)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        val adapter =
            PagerAdapter(childFragmentManager)

        adapter.addFragment(HomeFragment(),"Movies")
        adapter.addFragment(MyListFragment(),"My List")
        binding.viewpager.adapter = adapter
    }


}
