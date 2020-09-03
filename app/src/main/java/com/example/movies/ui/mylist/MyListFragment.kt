package com.example.movies.ui.mylist

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.DashBoardActivity
import com.example.movies.R
import com.example.movies.base.BaseFragment
import com.example.movies.dao.Movie
import com.example.movies.dao.MyMoviesDB
import com.example.movies.databinding.FragmentMyListBinding
import kotlinx.coroutines.launch


class MyListFragment : BaseFragment<FragmentMyListBinding>(), MyListAdapter.OnItemClickListener {

    private lateinit var db : MyMoviesDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = (activity as DashBoardActivity).db
    }

    override fun getViewBinding(): FragmentMyListBinding =
        FragmentMyListBinding.inflate(layoutInflater)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            add.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_pager_to_navigation_addMovie)
            }

            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)

                lifecycleScope.launch {
                    adapter = MyListAdapter(
                        db.movieDao().getAll(), this@MyListFragment
                    )
                }
            }
        }

    }

    override fun onItemClick(position: Movie) {

        val builder: AlertDialog = AlertDialog.Builder(context).create()

        builder.setTitle("Delete Movie!")
        builder.setMessage("Do you want to delete this movie?")

        builder.setButton(
            AlertDialog.BUTTON_POSITIVE, "Delete"
        ) { _, _ ->
            lifecycleScope.launch {
                db.movieDao().delete(position)
            }
            refreshFragment()
        }

        builder.setButton(
            AlertDialog.BUTTON_NEGATIVE, "Cancel"
        ) { _, _ ->
            builder.cancel()
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

    private fun refreshFragment(){
        val ft: FragmentTransaction = requireFragmentManager().beginTransaction()
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false)
        }
        ft.detach(this).attach(this).commit()
    }
}
