package org.kl.smartbuy.view.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import org.kl.smartbuy.R

class CategoryViewHolder : RecyclerView.ViewHolder {
    private var categoryCardView: CardView? = null
    var categoryImage: ImageView? = null
        private set
    var nameTextView: TextView? = null
        private set

    constructor(view: View) : super(view) {
        this.categoryCardView = view.findViewById(R.id.category_card_view)
        this.categoryImage = view.findViewById(R.id.item_category_image)
        this.nameTextView = view.findViewById(R.id.name_text_view)
    }
}