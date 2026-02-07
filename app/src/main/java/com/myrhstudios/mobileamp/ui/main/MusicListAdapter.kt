package com.myrhstudios.mobileamp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myrhstudios.mobileamp.databinding.ItemMusicListBinding

class MusicListAdapter(
    // private val items: List<MusicListItem>,
    private val onItemClick: (MusicListItem) -> Unit
) : RecyclerView.Adapter<MusicListAdapter.ViewHolder>() {

    private val items = mutableListOf<MusicListItem>()

    inner class ViewHolder(val binding: ItemMusicListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMusicListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.title.text = "%s (%d:%02d)".format(
            item.title,
            item.duration?.div(60),
            item.duration?.mod(60)
        )

        holder.binding.subtitle.text = when {
            item.artist != null && item.album != null ->
                "${item.artist}\n${item.album}"
            item.artist != null ->
                item.artist
            else ->
                "Unknown Artist"
        }

        holder.binding.root.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = items.size

    fun submitList(newItems: List<MusicListItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
