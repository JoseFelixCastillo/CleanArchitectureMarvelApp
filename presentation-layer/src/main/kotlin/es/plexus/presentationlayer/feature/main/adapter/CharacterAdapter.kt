package es.plexus.presentationlayer.feature.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.plexus.presentation_layer.databinding.CharacterItemBinding
import es.plexus.presentationlayer.feature.main.viewholder.CharacterViewHolder
import es.plexus.presentationlayer.model.CharacterVo

class CharacterAdapter(
    private var itemList: List<CharacterVo>,
    private val onItemSelected: (CharacterVo) -> Unit
) : RecyclerView.Adapter<CharacterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder =
        CharacterViewHolder(
            itemView = CharacterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.onBind(itemList[position], onItemSelected)
    }

    override fun getItemCount(): Int = itemList.size
}
