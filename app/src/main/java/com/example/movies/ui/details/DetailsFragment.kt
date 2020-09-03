package com.example.movies.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.movies.base.BaseFragment
import com.example.movies.databinding.FragmentDetailsBinding

class DetailsFragment : BaseFragment<FragmentDetailsBinding>() {

    private val detailsViewModel: DetailsViewModel by activityViewModels()
    private var movieId = 0

    override fun getViewBinding(): FragmentDetailsBinding =
        FragmentDetailsBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = requireArguments().getInt("id")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsViewModel.callData(movieId)
        detailsViewModel.movDetails.observe(viewLifecycleOwner, Observer {

            with(binding){
                if (it.adult) detAdults.visibility = View.VISIBLE
                detTitle.text = it.title

                Glide.with(detPhoto.context).load("https://image.tmdb.org/t/p/w500${it.backdrop_path}").into(detPhoto)
                detOverview.text = it.overview
                detDate.text = "Date: " + it.release_date
                detVote.text = "Vote: " + it.vote_count.toString()
                detRating.text = "Rate: " + it.vote_average.toString()
            }

        })
    }
}
