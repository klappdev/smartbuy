package org.kl.smartbuy.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

import org.kl.smartbuy.state.TabOrder
import org.kl.smartbuy.state.TabOrder.*
import org.kl.smartbuy.view.fragment.PurchaseFragment
import org.kl.smartbuy.view.fragment.CategoryFragment

class SectionPagerAdapter(private val size: Int, manager: FragmentManager) :
      FragmentStatePagerAdapter(manager) {

    override fun getItem(position: Int): Fragment {
        return when (TabOrder.findBy(position)) {
            CATEGORY_TAB -> CategoryFragment()
            PURCHASE_TAB -> PurchaseFragment()
        }
    }

    override fun getCount() = size
}