package com.example.desafiogabriela.pull

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiogabriela.model.ItemPullrequest
import com.example.desafiogabriela.R
import com.example.desafiogabriela.utils.loadImageUrl

import com.squareup.picasso.Picasso

class PullrequestAdapter(
    var list: List<ItemPullrequest>,
    private val repositoryListener: ClickListener
) :
    RecyclerView.Adapter<PullrequestAdapter.ViewHolderClass>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        return ViewHolderClass(
            LayoutInflater.from(parent.context).inflate(R.layout.item_pullrequest, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {

        val item = list[position]

        holder.icon.loadImageUrl(item.owner.image)
        holder.title.text = item.name
        holder.description.text = item.description
        holder.username.text = item.owner.username
        holder.createdAt.text = item.createdAt
        holder.itemView.setOnClickListener {
            repositoryListener.setOnClickListener(item)
        }
    }

    inner class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val icon: ImageView = itemView.findViewById(R.id.icone_pullrequest)
        val title: TextView = itemView.findViewById(R.id.titulo_pullrequest)
        val description: TextView = itemView.findViewById(R.id.boby_pullrequest)
        val username: TextView = itemView.findViewById(R.id.username_pullrequest)
        val createdAt: TextView = itemView.findViewById(R.id.fullname_pullrequest)
    }

    interface ClickListener {
        fun setOnClickListener(itemClick: ItemPullrequest)
    }
}
