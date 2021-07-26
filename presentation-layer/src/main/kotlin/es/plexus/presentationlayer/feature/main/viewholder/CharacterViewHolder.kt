package es.plexus.presentationlayer.feature.main.viewholder

import com.bumptech.glide.Glide
import es.plexus.presentation_layer.R
import es.plexus.presentation_layer.databinding.CharacterItemBinding
import es.plexus.presentationlayer.base.BaseViewHolder
import es.plexus.presentationlayer.model.CharacterVo

class CharacterViewHolder(itemView: CharacterItemBinding) :
    BaseViewHolder<CharacterVo>(itemView.root) {

    private val image by lazy { itemView.characterItemImage }
    private val name by lazy { itemView.characterItemName }
    private val description by lazy { itemView.characterItemDescription }
    private val container by lazy { itemView.root }

    override fun onBind(item: CharacterVo, callback: (CharacterVo) -> Unit) {
        name.text = item.name
        description.text = item.description
        Glide.with(container)
            .load("${item.thumbnail.path}/standard_xlarge.${item.thumbnail.extension}")
            .centerInside()
            .placeholder(R.drawable.img_marvel_placeholder)
            .into(image)
        container.setOnClickListener { callback(item) }
    }
}
