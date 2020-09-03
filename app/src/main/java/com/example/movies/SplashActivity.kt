package com.example.movies

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.movies.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        Handler().postDelayed(Runnable {
            if (!isOnline()) openNetworkDialog()
            else {
                val intent = Intent(this, DashBoardActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 800)

        setLogoAnimate()
    }


    private fun setLogoAnimate() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.right_animation)
        binding.splashLogoImg.startAnimation(animation)
    }


    private fun isOnline(): Boolean {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    private fun openNetworkDialog() {
        val builder: AlertDialog = AlertDialog.Builder(this).create()

        builder.setTitle("Network Error!")
        builder.setMessage("Connect to the network and try again!")

        builder.setButton(
            AlertDialog.BUTTON_POSITIVE, "Try Again"
        ) { _, _ ->
            val intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
            finish()
        }

        builder.setButton(
            AlertDialog.BUTTON_NEGATIVE, "Cancel"
        ) { _, _ ->
            finish()
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

}