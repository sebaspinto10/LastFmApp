package com.example.lastfmapp.ui.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lastfmapp.core.BaseViewHolder
import com.example.lastfmapp.data.model.Artist
import com.example.lastfmapp.databinding.ArtistItemViewBinding
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class ArtistAdapter(private val artistList: List<Artist>) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            ArtistItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtistViewHolder(itemBinding, parent.context)
    }

    override fun getItemCount(): Int = artistList.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is ArtistViewHolder -> holder.bind(artistList[position], position + 1)
        }
    }

    private inner class ArtistViewHolder(val binding: ArtistItemViewBinding, val context: Context) :
        BaseViewHolder<Artist>(binding.root) {
        @SuppressLint("SetTextI18n")
        override fun bind(item: Artist, position: Int) {
            binding.tvNumberTop.text = (position).toString()

            Glide.with(context).load(item.image[0].textval).centerCrop().into(binding.artistPicture)
            binding.tvArtistName.text = item.name

            if (item.listeners.toInt() > 0) {
                binding.tvListeners.text = "${
                    DecimalFormat(
                        "#,###",
                        DecimalFormatSymbols(Locale.ITALIAN)
                    ).format(item.listeners.toInt())
                } listeners"
            }

        }

    }
}