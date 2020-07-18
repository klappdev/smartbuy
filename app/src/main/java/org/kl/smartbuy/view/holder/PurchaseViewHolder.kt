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

    var itemImage: ImageView? = null
        private set
    var editImage: ImageView? = null
        private set
    var deleteImage: ImageView? = null
        private set

    constructor(view: View) : super(view) {
        this.nameTextView = view.findViewById(R.id.name_purchase_text_view)
        this.dateTextView = view.findViewById(R.id.date_purchase_text_view)

        this.itemImage = view.findViewById(R.id.item_purchase_image)
        this.editImage = view.findViewById(R.id.edit_purchase_image)
        this.deleteImage = view.findViewById(R.id.delete_purchase_image)
    }
}