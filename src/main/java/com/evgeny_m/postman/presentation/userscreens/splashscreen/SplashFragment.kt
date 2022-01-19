package com.evgeny_m.postman.presentation.userscreens.splashscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.evgeny_m.postman.data.oldcode.chatsfirebase.result
import com.evgeny_m.postman.databinding.FragmentSplashBinding
import com.evgeny_m.postman.drawerLayout


class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        result.observe(viewLifecycleOwner, Observer {
            when (it) {
                "isPending" -> {

                }
                "isSuccess" -> {
                    parentFragmentManager.popBackStack()
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                }
                "isFail" -> {
                    parentFragmentManager.popBackStack()
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                    Toast.makeText(
                        requireContext(),
                        "unable to update the feed, check your internet connection",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {
                    //binding.progress.visibility = View.GONE
                }
            }
        })


        return binding.root
    }


}