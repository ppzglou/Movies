package com.example.movies.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.base.BaseFragment
import com.example.movies.R
import com.example.movies.data.Result
import com.example.movies.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment<FragmentHomeBinding>(), MoviesAdapter.OnItemClickListener  {

    private val homeViewModel: HomeViewModel by activityViewModels()
    var listmovies: MutableList<Result> = mutableListOf()

    override fun getViewBinding(): FragmentHomeBinding =
        FragmentHomeBinding.inflate(layoutInflater)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.response.observe(viewLifecycleOwner, Observer {
            progress_bar.visibility = View.GONE
            if (it.isNotEmpty()){
                listmovies.addAll(it)
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }

        })
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = MoviesAdapter(
                listmovies,
                this@HomeFragment
            )
        }

        homeViewModel.callData()


        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    progress_bar.visibility = View.VISIBLE
                    homeViewModel.callData()

                }
            }
        })

    }

    override fun onItemClick(position: Int) {
        val bundle =
        bundleOf("id" to listmovies[position].id)
        findNavController().navigate(R.id.action_navigation_pager_to_navigation_details,bundle)
    }

}
