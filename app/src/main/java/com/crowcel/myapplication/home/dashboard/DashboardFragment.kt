package com.crowcel.myapplication.home.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.crowcel.myapplication.R
import com.crowcel.myapplication.databinding.FragmentDashboardBinding
import com.crowcel.myapplication.model.Film
import com.crowcel.myapplication.utils.Preferences
import com.google.firebase.database.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class DashboardFragment : Fragment() {

    private lateinit var preferences: Preferences
    private lateinit var mDatabase: DatabaseReference
    private var dataList = ArrayList<Film>()

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    fun OnActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        preferences = Preferences(requireActivity().applicationContext)
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")

        binding.tvNama.setText(preferences.getValues("nama"))

        if (!preferences.getValues("saldo").equals("")) {
            currency(preferences.getValues("saldo")!!.toDouble(), binding.tvSaldo)
        } else {
            Glide.with(this)
                .load(preferences.getValues("url"))
                .apply(RequestOptions.circleCropTransform())
                .into(binding.ivProfile)

            binding.rvNowPlaying.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.rvComingSoon.layoutManager = LinearLayoutManager(context)
        }


    }



    private fun currency(harga : Double, TextView : TextView){
        val localID = Locale("in", "ID")
        val format = NumberFormat.getCurrencyInstance(localID)
        TextView.setText(format.format(harga))

    }

    private fun getData(){
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()

                for (getdataSnapshot in snapshot.children){
                    val film = getdataSnapshot.getValue(Film::class.java)
                    dataList.add(film!!)
                }

                binding.rvNowPlaying.adapter = NowPlayingAdapter(dataList){
                    val bundle = Bundle()
                    bundle.putParcelable("data", it)
                }

                binding.rvComingSoon.adapter = ComingSoonAdapter(dataList){
                    val bundle = Bundle()
                    bundle.putParcelable("data", it)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, ""+error.message, Toast.LENGTH_LONG).show()
            }

        })
    }



}