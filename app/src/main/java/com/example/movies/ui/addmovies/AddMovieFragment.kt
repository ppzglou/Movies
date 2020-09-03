package com.example.movies.ui.addmovies

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.example.movies.DashBoardActivity
import com.example.movies.R
import com.example.movies.base.BaseFragment
import com.example.movies.dao.Movie
import com.example.movies.dao.MyMoviesDB
import com.example.movies.databinding.FragmentAddMovieBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import java.io.File


class AddMovieFragment : BaseFragment<FragmentAddMovieBinding>() {

    private val GALLERY_PHOTO = 111
    private var filePath = ""

    private lateinit var db : MyMoviesDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = (activity as DashBoardActivity).db
    }

    override fun getViewBinding(): FragmentAddMovieBinding =
        FragmentAddMovieBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            moviePhoto.setOnClickListener {
                if (setupPermissions(view.context)) {
                    val intent = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    startActivityForResult(intent, GALLERY_PHOTO)
                } else
                    Toast.makeText(context, "You don't have storage permission", Toast.LENGTH_SHORT)
                        .show()
            }

            add.setOnClickListener {
                lifecycleScope.launch {
                    db.movieDao().insert(Movie(
                        java.util.Calendar.getInstance().toString(),
                        movieTitle.text.toString(),
                        movieOverview.text.toString(),
                        movieRating.text.toString(),
                        filePath
                    ))
                }
                reloadPrevFragment()
                activity?.onBackPressed()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_PHOTO && resultCode == RESULT_OK) {
            if (data!!.data != null) {
                filePath = GetFilePathFromDevice.getPath(context, data!!.data)

                val file = File(filePath)
                //Glide.with(this).load(file).into(binding.photo)
                Picasso.get().load(file).into(binding.photo)
            }
        }
    }

    private fun setupPermissions(context: Context): Boolean {
        val permissionR = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val permissionW = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val grand = PackageManager.PERMISSION_GRANTED
        return !(permissionR != grand || permissionW != grand)
    }

    private fun reloadPrevFragment(){
        val currentFragment: Fragment? =
            requireActivity().supportFragmentManager.findFragmentById(R.id.mylist)
        val fragmentTransaction: FragmentTransaction =
            requireFragmentManager().beginTransaction()
        if (currentFragment != null) {
            fragmentTransaction.detach(currentFragment)
        }
        if (currentFragment != null) {
            fragmentTransaction.attach(currentFragment)
        }
        fragmentTransaction.commit()
    }
}
