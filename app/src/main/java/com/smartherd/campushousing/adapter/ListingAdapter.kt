package com.smartherd.campushousing.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.smartherd.campushousing.ListingDetailsActivity
import com.smartherd.campushousing.R
import com.smartherd.campushousing.data.Listing

class ListingAdapter :
    RecyclerView.Adapter<ListingAdapter.ListingViewHolder>() {

    private var listings: List<Listing> = emptyList()

    fun submitList(data: List<Listing>) {
        listings = data
        notifyDataSetChanged()
    }

    class ListingViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val title: TextView =
            itemView.findViewById(R.id.titleText)

        val price: TextView =
            itemView.findViewById(R.id.priceText)

        val image: ImageView =
            itemView.findViewById(R.id.listingImage)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListingViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_listings, parent, false)

        return ListingViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ListingViewHolder,
        position: Int
    ) {

        val item = listings[position]

        holder.title.text = item.title

        holder.price.text = "BWP ${item.price}"

        holder.image.setImageResource(item.imageResId)

        // CLICK LISTENER
        holder.itemView.setOnClickListener {

            val intent = Intent(
                holder.itemView.context,
                ListingDetailsActivity::class.java
            )

            intent.putExtra("listingId", item.id)

            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = listings.size
}