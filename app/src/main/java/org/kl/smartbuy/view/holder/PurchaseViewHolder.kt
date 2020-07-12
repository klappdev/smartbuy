package org.kl.smartbuy.view.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import org.kl.smartbuy.R

class PurchaseViewHolder : RecyclerView.ViewHolder {
    var nameTextView: TextView? = null
        private set
    var dateTextView: TextView? = null
        private set

    var itemImageView: ImageView? = null
        private set
    var editImageView: ImageView? = null
        private set
    var deleteImageView: ImageView? = null
        private set

    constructor(view: View) : super(view) {
        this.nameTextView = view.findViewById(R.id.name_purchase_text_view)
        this.dateTextView = view.findViewById(R.id.date_purchase_text_view)

        this.itemImageView = view.findViewById(R.id.item_purchase_image)
        this.editImageView = view.findViewById(R.id.edit_purchase_image)
        this.deleteImageView = view.findViewById(R.id.delete_purchase_image)
    }
}