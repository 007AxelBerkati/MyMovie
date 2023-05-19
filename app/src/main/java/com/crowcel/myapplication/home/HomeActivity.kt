package com.crowcel.myapplication.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.crowcel.myapplication.R
import com.crowcel.myapplication.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentHome = DashboardFragment()
        val fragmentTicket = TicketFragment()
        val fragmentProfile = ProfileFragment()
        setFragment(fragmentHome)

        binding.ivMenu1.setOnClickListener {
            setFragment(fragmentHome)
            changeIcon(binding.ivMenu1, R.drawable.ic_home_active)
            changeIcon(binding.ivMenu2, R.drawable.ic_tiket)
            changeIcon(binding.ivMenu3, R.drawable.ic_profile)
        }
        binding.ivMenu2.setOnClickListener {
            setFragment(fragmentTicket)
            changeIcon(binding.ivMenu1, R.drawable.ic_home)
            changeIcon(binding.ivMenu2, R.drawable.ic_tiket_active)
            changeIcon(binding.ivMenu3, R.drawable.ic_profile)
        }
        binding.ivMenu3.setOnClickListener {
            setFragment(fragmentProfile)
            changeIcon(binding.ivMenu1, R.drawable.ic_home)
            changeIcon(binding.ivMenu2, R.drawable.ic_tiket)
            changeIcon(binding.ivMenu3, R.drawable.ic_profile_active)
        }
    }

    private fun setFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.layout_frame, fragment)
            commit()
        }
    }

    private fun changeIcon(imageView: ImageView, image: Int){
        imageView.setImageResource(image)
    }
}