package com.example.lastfmapp.ui.track.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lastfmapp.core.BaseViewHolder
import com.example.lastfmapp.data.model.Track
import com.example.lastfmapp.databinding.TrackItemViewBinding
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class TrackAdapter(private val trackList: List<Track>) : RecyclerView.Adapter<BaseViewHolder<*>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemViewBinding =
            TrackItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackViewHolder(itemViewBinding, parent.context)
    }

    override fun getItemCount(): Int = trackList.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is TrackViewHolder -> {
                holder.bind(trackList[position], position + 1)
            }
        }
    }

    private inner class TrackViewHolder(val binding: TrackItemViewBinding, val context: Context) :
        BaseViewHolder<Track>(binding.root) {
        override fun bind(item: Track, position: Int) {
            binding.tvNumberTop.text = (position).toString()

            Glide.with(context).load(item.image[0].textval).centerCrop().into(binding.trackPicture)
            binding.tvTrackName.text = item.name

            if (item.listeners.toInt() > 0) {
                binding.tvListeners.text = DecimalFormat(
                    "#,###",
                    DecimalFormatSymbols(Locale.ITALIAN)
                ).format(item.listeners.toInt())
            }
        }
    }
}