package com.crowcel.myapplication.home.dashboard

import androidx.recyclerview.widget.RecyclerView
import com.crowcel.myapplication.model.Film

class NowPlayingAdapter(private var data: List<Film>, private val listener: (Film) -> Unit) : RecyclerView.Adapter<NowPlayingAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_now_playing, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    }

}
